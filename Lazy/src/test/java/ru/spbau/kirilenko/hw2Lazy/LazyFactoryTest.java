package ru.spbau.kirilenko.hw2Lazy;

import org.junit.Test;

import java.util.function.Supplier;

import static org.junit.Assert.*;

public class LazyFactoryTest {
    private static final Supplier<Integer> counter2 = new Supplier<Integer>() {
        private int number = 0;
        @Override
        public Integer get() {
            return number++;
        }
    };

    @Test
    public void testSingleThreadLazy() throws Exception {
        Supplier<Integer> counter = new Supplier<Integer>() {
            private int number = 0;
            @Override
            public Integer get() {
                return number++;
            }
        };

        Lazy<Integer> lazy = LazyFactory.createSingleThreadLazy(counter);

        assertTrue(lazy.get().equals(0));
        assertTrue(lazy.get().equals(0));
    }
}