package ru.spbau.kirilenko.hw0Lazy;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Class that tests all versions of lazy compute
 * Includes multi thread race condition tests
 */
public class LazyFactoryTest {
    private final ArrayList<Boolean> counter = new ArrayList<>();

    /**
     * Init test check list
     */
    @Before
    public void initCounter() {
        counter.clear();
    }

    /**
     * Method tests that single thread lazy built without errors
     * and get method returns correct value
     */
    @Test
    public void testSingleThreadLazySimple() {
        Lazy<String> lazy = LazyFactory.createSingleThreadLazy(() -> "Hello");

        assertThat(lazy.get(), is("Hello"));
    }

    /**
     * Method that tests laziness of computing value in lazy
     */
    @Test
    public void testSingleThreadLazyLaziness() {
        Lazy<Boolean> lazy = LazyFactory.createSingleThreadLazy(() -> {
            counter.add(true);
            return true;});

        counter.add(false);

        assertThat(counter.size(), is(1));
        assertThat(lazy.get(), is(true));
        assertThat(counter.size(), is(2));
        assertFalse(counter.get(0));
        assertTrue(counter.get(1));
    }

    /**
     * Method that tests number of calls of supplier get method
     */
    @Test
    public void testSingleThreadLazySingleComputing() {
        Lazy<Boolean> lazy = LazyFactory.createSingleThreadLazy(() -> {
            counter.add(true);
            return true;});

        counter.add(false);

        assertThat(counter.size(), is(1));
        assertThat(lazy.get(), is(true));
        assertThat(counter.size(), is(2));

        for (int i = 0; i < 100; i++) {
            assertThat(lazy.get(), is(true));
        }

        assertThat(counter.size(), is(2));
    }

    /**
     * Method that many times runs many lazies in multiple threads and check that computation
     * in them was only once
     * @throws InterruptedException if any thread was interrupted
     */
    @Test
    public void testMultiThreadLazySingleComputing() throws InterruptedException {
        for (int i = 0; i < 50; i++) {
            ArrayList<Lazy> lazyPack = new ArrayList<>();
            ArrayList<Thread> threadPack = new ArrayList<>();
            for (int j = 0; j < 50; j++) {
                lazyPack.add(LazyFactory.createMultiThreadLazy(() -> {
                    synchronized (counter) {
                        counter.add(true);
                    }
                    return true;
                }));

                threadPack.add(new Thread(() -> {
                    for (Lazy lazy : lazyPack) {
                        lazy.get();
                    }
                }));
            }

            for (Thread thread : threadPack) {
                thread.start();
            }

            for (Thread thread : threadPack) {
                thread.join();
            }
        }

        assertThat(counter.size(), is(2500));
    }

    /**
     * Method that many times runs many lazies with random values that are returned by supplier
     * in multiple threads and check that computation in them was only once
     * @throws InterruptedException if any thread was interrupted
     */
    @Test
    public void testMultiThreadLazyRandomComputing() throws InterruptedException {
        Random rand = new Random();
        Lazy<Integer> lazy = LazyFactory.createMultiThreadLazy(rand::nextInt);

        for (int i = 0; i < 50; i++) {
            ArrayList<Thread> threadPack = new ArrayList<>();
            ArrayList<Boolean> check = new ArrayList<>();
            for (int j = 0; j < 50; j++) {
                threadPack.add(new Thread(() -> {
                    synchronized (check) {
                        int res = lazy.get();
                        check.add(lazy.get().equals(res));
                    }
                }));
            }

            for (Thread thread : threadPack) {
                thread.start();
            }

            for (Thread thread : threadPack) {
                thread.join();
            }

            for (Boolean result : check) {
                assertTrue(result);
            }
        }
    }
}