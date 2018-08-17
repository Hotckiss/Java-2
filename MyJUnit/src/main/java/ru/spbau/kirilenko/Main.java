package ru.spbau.kirilenko;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Command line application that allows to run tests in classes
 */
public class Main {
    private static final String URL_PREFIX = "file:";

    /**
     * Main class that gets paths and runs tests in classes specified with them
     * @param args user arguments
     */
    public static void main(String[] args) {
        List<String> classes = new ArrayList<>();
        List<URL> paths = new ArrayList<>();

        for (String arg : args) {
            if (arg.equals("-h") || arg.equals("--help")) {
                showHelp();
            }
        }

        addURL(System.getProperty("user.dir"), paths);
        addClasspath(args, classes, paths);

        URL[] urls = new URL[paths.size()];
        paths.toArray(urls);
        URLClassLoader classLoader = new URLClassLoader(urls);

        for (String className : classes) {
            testClass(className, classLoader);
        }
    }

    private static void testClass(@NotNull String className, @NotNull URLClassLoader classLoader) {
        try {
            Class clazz = classLoader.loadClass(className);
            List<TestScore> testScores = Tester.run(clazz);
            for (TestScore score: testScores) {
                switch (score.getResult()) {
                    case SUCCESS: {
                        System.out.println(score.getName() + " passed, time = " + score.getRunningTime());
                        break;
                    }

                    case FAILURE: {
                        System.out.print(score.getName() + " failed\n");
                        if (score.getFailureReason() == null) {
                            System.out.printf("Cause: %s.\n", "not expected exception");
                        } else {
                            System.out.printf("Cause: %s.\n", score.getFailureReason().getMessage());
                        }

                        System.out.println("time = " + score.getRunningTime());
                        break;
                    }

                    case IGNORE: {
                        System.out.println(score.getName() + " ignored!");
                        System.out.println("Reason: " + score.getIgnoreReason());
                        break;
                    }
                }
                System.out.println("");
            }
        } catch (ClassNotFoundException e) {
            System.out.println(className + " not found!");
        } catch (InstantiationException e) {
            System.out.println(className + " not instanced!");
        } catch (IllegalAccessException e) {
            System.out.println(className + " not accessible!");
        } catch (InvocationException e) {
            System.out.printf("%s in method %s: %s.\n",
                    e.getCause().getClass().getName(),
                    e.getMethod().getName(),
                    e.getCause().getMessage());
        }
    }

    private static void showHelp() {
        System.out.println("Add classes for testing, -dir to add classpath.");
    }

    private static void addURL(@NotNull String path, @NotNull List<URL> paths) {
        try {
            paths.add(new URL(URL_PREFIX + path));
        } catch (MalformedURLException e) {
            System.err.println("Bad path!");
        }
    }

    private static void addClasspath(@NotNull String[] args,
                                     @NotNull List<String> classes,
                                     @NotNull List<URL> paths) {
        boolean additionalPath = false;
        for (String arg: args) {
            if (additionalPath) {
                String cp = Paths.get(arg).toAbsolutePath().toString() + File.separator;
                addURL(cp, paths);
                additionalPath = false;
            } else {
                if (arg.equals("-dir")) {
                    additionalPath = true;
                } else {
                    classes.add(arg);
                }
            }
        }
    }
}