package ru.spbau.kirilenko.hw2Lazy;

import org.junit.Test;

import static org.junit.Assert.*;

public class LazyTest {
    @Test
    public void testSum2() throws Exception {
        assertEquals(5, Lazy.sum2(2, 3));
    }
}