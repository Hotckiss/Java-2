package ru.spbau.kirilenko;

import lombok.Getter;
import org.junit.Assert.*;
import org.junit.Test;
import ru.spbau.kirilenko.annotations.After;
import ru.spbau.kirilenko.annotations.AfterClass;
import ru.spbau.kirilenko.annotations.Before;
import ru.spbau.kirilenko.annotations.BeforeClass;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Testing all usage ways of tester
 */
@SuppressWarnings("WeakerAccess")
public class TesterTest {
    /**
     * Successful test
     */
    @Test
    public void simpleSuccessTest() throws IllegalAccessException,
            InvocationException, InstantiationException {
        TestScore score = Tester.run(SimpleClass.class).get(0);

        assertEquals(TestResult.SUCCESS, score.getResult());
        assertTrue(SimpleClass.isCalled());
    }

    /**
     * Failed test
     */
    @Test
    public void simpleFailureTest() throws IllegalAccessException,
            InvocationException, InstantiationException {
        TestScore score = Tester.run(ErrorCannon.class).get(0);

        assertEquals(TestResult.FAILURE, score.getResult());
    }

    /**
     * Ignored test
     */
    @Test
    public void simpleIgnoreTest() throws IllegalAccessException, InvocationException,
            InstantiationException {
        TestScore score = Tester.run(Ignored.class).get(0);

        assertEquals(TestResult.IGNORE, score.getResult());
        assertEquals(Ignored.REASON, score.getIgnoreReason());
        assertFalse(Ignored.isCalled());
    }

    /**
     * Test with expected exception
     */
    @Test
    public void expectedSuccessTest() throws IllegalAccessException,
            InvocationException, InstantiationException {
        TestScore score = Tester.run(ExpectedThrown.class).get(0);

        assertEquals(TestResult.SUCCESS, score.getResult());
    }

    /**
     * Test with Before annotations
     */
    @Test
    public void beforeTest() throws IllegalAccessException, InvocationException,
            InstantiationException {
        TestScore score = Tester.run(BeforeTester.class).get(0);
        assertEquals(TestResult.SUCCESS, score.getResult());
    }

    /**
     * Test with After annotations
     */
    @Test
    public void afterTest() throws IllegalAccessException, InvocationException,
            InstantiationException {
        TestScore score = Tester.run(AfterTester.class).get(0);
        assertEquals(TestResult.SUCCESS, score.getResult());
        assertEquals(2, AfterTester.getCalls());
    }

    /**
     * Test with BeforeClass annotations
     */
    @Test
    public void beforeClassTest() throws IllegalAccessException,
            InvocationException, InstantiationException {
        TestScore score = Tester.run(BeforeClassTester.class).get(0);
        assertEquals(TestResult.SUCCESS, score.getResult());
    }

    /**
     * Test with AfterClass annotations
     */
    @Test
    public void afterClassTest() throws IllegalAccessException,
            InvocationException, InstantiationException {
        TestScore score = Tester.run(AfterClassTester.class).get(0);
        assertEquals(TestResult.SUCCESS, score.getResult());
        assertEquals(3, AfterClassTester.getCalls());
    }

    public static class SimpleClass {
        public SimpleClass(){}
        @Getter private static boolean called = false;

        @ru.spbau.kirilenko.annotations.Test
        public void test() {
            called = true;
        }
    }

    public static class ErrorCannon {
        @ru.spbau.kirilenko.annotations.Test
        public void test() {
            throw new Error();
        }
    }

    public static class Ignored {
        public static final String REASON = "reason";
        @Getter private static boolean called = false;

        @ru.spbau.kirilenko.annotations.Test(ignoreReason = REASON)
        public void test() {
            called = true;
        }
    }

    public static class ExpectedThrown {
        @ru.spbau.kirilenko.annotations.Test(expected = "Exception")
        public void test() throws Exception {
            throw new Exception();
        }
    }

    public static class BeforeTester {
        private int beforeCalls = 0;

        @Before
        public void before() {
            beforeCalls++;
        }

        @ru.spbau.kirilenko.annotations.Test
        public void test() {
            assertEquals(1, beforeCalls);
        }
    }

    public static class AfterTester {
        @Getter private static int calls = 0;

        @After
        public void after() {
            assertEquals(calls, 1);
            calls++;
        }

        @ru.spbau.kirilenko.annotations.Test
        public void test() {
            calls++;
        }
    }

    public static class BeforeClassTester {
        private int beforeCalls = 0;

        @BeforeClass
        public void beforeClass() {
            beforeCalls++;
        }

        @ru.spbau.kirilenko.annotations.Test
        public void test() {
            assertEquals(1, beforeCalls);
        }

        @ru.spbau.kirilenko.annotations.Test
        public void test2() {
            assertEquals(1, beforeCalls);
        }
    }

    public static class AfterClassTester {
        @Getter private static int calls = 0;

        @AfterClass
        public void afterClass() {
            assertEquals(calls, 2);
            calls++;
        }

        @ru.spbau.kirilenko.annotations.Test
        public void test() {
            calls++;
        }

        @ru.spbau.kirilenko.annotations.Test
        public void test2() {
            calls++;
        }
    }
}