package ru.spbau.kirilenko.hw1ThreadPool;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Class that provides tests to all methods of thread pool
 */
public class ThreadPoolImplTest {
    private ThreadPoolImpl pool;

    @Before
    public void init() {
        pool = new ThreadPoolImpl(4);
    }

    @After
    public void stopPool() {
        pool.shutdown();
    }
    /**
     * Testing thread pool with some number of simple tasks
     */
    @Test
    public void testPoolSimple() {
        LightFuture<Integer> task1 = pool.submit(() -> 2 + 2);
        LightFuture<Integer> task2 = pool.submit(() -> 7);
        LightFuture<Long> task3 = pool.submit(() -> {
            long exp = 1;

            for (int i = 0; i < 50; i++) {
                exp *= 2;
            }

            return exp;
        });
        assertThat(task1.get(), is(4));
        assertThat(task2.get(), is(7));
        assertThat(task3.get(), is(1125899906842624L));
    }

    /**
     * Testing correct number of the threads
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPoolArguments() {
        pool = new ThreadPoolImpl(-1);
    }

    /**
     * Testing thread pool correctness with single thread
     */
    @Test
    public void testPoolSingleThread() {
        pool = new ThreadPoolImpl(1);
        LightFuture<Integer> task1 = pool.submit(() -> 2 + 2);
        LightFuture<Integer> task2 = pool.submit(() -> 7);

        assertThat(task1.get(), is(4));
        assertThat(task2.get(), is(7));
    }

    /**
     * Testing isReady method of thread pool task
     */
    @Test
    public void testPoolIsReady() {
        LightFuture<Integer> task = pool.submit(() -> 5553535);

        assertThat(task.get(), is(5553535));
        assertTrue(task.isReady());
    }

    /**
     * Testing then apply method with the task result
     */
    @Test
    public void testPoolThenApply() {
        LightFuture<Integer> task1 = pool.submit(() -> 5553535);
        LightFuture<String> task2 = task1.thenApply((num) -> String.valueOf(num) + "123");
        LightFuture<Integer> task3 = task2.thenApply(String::length);

        assertThat(task1.get(), is(5553535));
        assertThat(task2.get(), is("5553535123"));
        assertThat(task3.get(), is(10));
    }

    /**
     * Test that exception thrown if exception during execution occurred
     */
    @Test(expected = LightExecutionException.class)
    public void testLightFutureWithException() {
        LightFuture<Integer> task1 = pool.submit(() -> {
            throw new RuntimeException();
        });

        task1.get();
    }

    /**
     * Testing thread pool with many tasks
     */
    @Test
    public void testThreadPoolManyTasks (){
        ArrayList<LightFuture<Integer> > res = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            final Integer n = i;
            res.add(pool.submit(() -> 2 * n).thenApply(num -> num + 5));
        }

        for (int i = 0; i < 100; i++) {
            assertThat(res.get(i).get(), is(2 * i + 5));
        }
    }

    /**
     * Checks that new task from thenApply executes after parent task
     */
    @Test
    public void testThenApply() {
        final ArrayList<Integer> counter = new ArrayList<>();

        LightFuture<Integer> task1 = pool.submit(() -> {
            counter.add(1);
            int cnt = 0;
            for (int i = 1; i <= 100000; i++) {
                cnt += i;
            }

            return cnt;
        });
        LightFuture<Integer> task2 = task1.thenApply((i) -> {
            counter.add(2);
            return 7;
        });
        LightFuture<Integer> task3 = task2.thenApply((i) -> {
            counter.add(3);
            return 2 * 2;
        });

        task1.get();
        task2.get();
        task3.get();

        assertEquals(3, counter.size());
        assertThat(counter.get(0), is(1));
        assertThat(counter.get(1), is(2));
        assertThat(counter.get(2), is(3));
    }

    /**
     * Tests thread pool on race condition.
     */
    @Test
    public void testPoolRaceCondition() {
        ThreadPoolImpl pool = new ThreadPoolImpl(20);
        Supplier<Integer> task = () -> 42;
        Function<Integer, Integer> after = (i) -> 0;

        for (int i = 0; i < 10000; i++) {
            LightFuture<Integer> result = pool.submit(task);
            result.thenApply(after);
        }
    }

    /**
     * Test blocking thread in then apply
     */
    @Test
    public void testThenApplyBlocking() {
        Supplier<Integer> task = () -> 42;
        Function<Integer, Integer> addTwo = (a) -> a + 2;
        ThreadPoolImpl pool1 = new ThreadPoolImpl(1);

        LightFuture<Integer> parent = pool1.submit(task);
        parent.get();
        assertThat(parent.thenApply(addTwo).get(), is(44));
    }
}