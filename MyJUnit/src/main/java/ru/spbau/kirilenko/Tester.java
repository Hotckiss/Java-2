package ru.spbau.kirilenko;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import ru.spbau.kirilenko.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.spbau.kirilenko.TestResult.*;

/**
 * Class for running tests
 */
public class Tester {
    /**
     * Runs all the tests(without ignore reason) in the given class. The class must have a public constructor
     * with no arguments
     * @param clazz the class containing the tests
     * @return a list containing TestScore objects for all the tests in the class
     * @throws IllegalAccessException if the class or its constructor is not accessible
     * @throws InstantiationException if the class if impossible to instantiate
     * @throws InvocationException if a @Before, @After, @BeforeClass or @AfterClass method throws an exception
     */
    @NotNull
    public static List<TestScore> run(@NotNull Class clazz)
            throws IllegalAccessException, InstantiationException, InvocationException {
        Object instance = clazz.newInstance();
        List<TestScore> scores;
        List<Method> beforeMethods;
        List<Method> afterMethods;
        Runner runner = new Runner(instance);

        beforeMethods = loadMethods(clazz, Before.class);
        afterMethods = loadMethods(clazz, After.class);
        runAnnotated(clazz, BeforeClass.class, runner);

        try {
            scores = getStream(clazz, Test.class)
                    .map(method -> {
                        beforeMethods.forEach(runner::run);
                        TestScore score = runTest(method, instance);
                        afterMethods.forEach(runner::run);
                        return score;
                    })
                    .collect(Collectors.toList());
        } catch (InvocationError e) {
            throw new InvocationException(e.getMethod(), e.getCause());
        }

        runAnnotated(clazz, AfterClass.class, runner);

        return scores;
    }

    /**
     * Class for running methods with the instance that was provided to the constructor.
     */
    @AllArgsConstructor
    private static class Runner {
        private final Object instance;

        private void run(@NotNull Method method) {
            try {
                method.invoke(instance);
            } catch (IllegalAccessException e) {
                throw new InvocationError(method, e);
            } catch (InvocationTargetException e) {
                throw new InvocationError(method, e.getCause());
            }
        }
    }

    /**
     * Single test runner
     * @param method method to run
     * @param instance instance for testing
     * @return object with test result
     */
    @NotNull
    private static TestScore runTest(@NotNull Method method, @NotNull Object instance) {
        Test testAnnotation = method.getAnnotation(Test.class);
        TestScore score = new TestScore(method.getName());
        String expectedException = testAnnotation.expected();

        if (!testAnnotation.ignoreReason().equals(Test.ACTIVE)) {
            score.setResult(IGNORE);
            score.setIgnoreReason(testAnnotation.ignoreReason());
            return score;
        }

        long startTime = 0;
        long finishTime = 0;
        try {
            startTime = System.currentTimeMillis();
            method.invoke(instance);
            finishTime = System.currentTimeMillis();
            if (expectedException.equals(Test.NO_EXCEPTIONS)) {
                score.setResult(SUCCESS);
            } else {
                score.setResult(FAILURE);
            }
        } catch (InvocationTargetException e) {
            finishTime = System.currentTimeMillis();
            Throwable t = e.getCause();
            Class errorClass = t.getClass();
            if (expectedException.equals(errorClass.getCanonicalName()) ||
                    expectedException.equals(errorClass.getSimpleName())) {
                score.setResult(SUCCESS);
            } else {
                score.setResult(FAILURE);
                score.setFailureReason(t);
            }
        } catch (IllegalAccessException e) {
            score.setResult(FAILURE);
            score.setFailureReason(e);
        }
        score.setRunningTime(finishTime - startTime);

        return score;
    }

    private static void runAnnotated(@NotNull Class clazz,
                                     @NotNull Class<? extends Annotation> annotation,
                                     @NotNull Runner runner)
            throws InvocationException {
        try {
            getStream(clazz, annotation).forEach(runner::run);
        } catch (InvocationError e) {
            throw new InvocationException(e.getMethod(), e.getCause());
        }
    }

    @NotNull
    private static List<Method> loadMethods(Class clazz, Class<? extends Annotation> annotation) {
        return getStream(clazz, annotation).collect(Collectors.toList());
    }

    private static Stream<Method> getStream(Class clazz, Class<? extends Annotation> annotation) {
        return Arrays.stream(clazz.getMethods())
                .filter(method -> method.getAnnotation(annotation) != null);
    }

    private static class InvocationError extends Error {
        @Getter private Method method;

        private InvocationError(@NotNull Method method, @NotNull Throwable cause) {
            super(cause);
            this.method = method;
        }
    }
}
