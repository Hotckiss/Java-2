package ru.spbau.kirilenko;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Paths;

import static java.lang.System.currentTimeMillis;

/**
 * Console application that compare speed of single-thread
 * and fork-join hashers on same directories
 */
public class Main {
    /**
     * Method that compares hash computation of specified directories or
     * current directory if nothing was deligated as arguments
     * @param args array of directories to compute
     */
    public static void main(@NotNull String[] args) {
        if (args.length == 0) {
            comparision(new String[] { System.getProperty("user.dir") });
        } else {
            comparision(args);
        }
    }

    /**
     * Computes hashes of directories with two different
     * implementations and compare them
     * @param paths String[] array with paths
     */
    private static void comparision(@NotNull String[] paths) {
        final DirsMD5 hasher = new DirsMD5();
        final DirsMD5 concurrentHasher = new DirsMD5(4);
        for (String path : paths) {
            System.out.println(path + ":");
            try {
                long time = currentTimeMillis();
                System.out.println("MD5: " + hasher.computeHash(Paths.get(path)));
                System.out.printf("Time single thread %d milliseconds\n", currentTimeMillis() - time);

                time = currentTimeMillis();
                System.out.println("MD5: " + concurrentHasher.computeHash(Paths.get(path)));
                System.out.printf("Time multi thread %d milliseconds\n", currentTimeMillis() - time);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
