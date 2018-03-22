package ru.spbau.kirilenko.hw3TicTac.logic;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Calss that tests all methods of generic pair class
 */
public class MyPairTest {
    private MyPair<Integer, String> testPair;

    /**
     * Init test pair
     */
    @Before
    public void init() {
        testPair = new MyPair<>(5, "test");
    }

    /**
     * Testing get first element of the pair
     */
    @Test
    public void testGetFirst() {
        assertThat(testPair.getFirst(), is(5));
    }

    /**
     * Testing get second element of the pair
     */
    @Test
    public void testGetSecond() {
        assertThat(testPair.getSecond(), is("test"));
    }

    /**
     * Testing set first element of the pair
     */
    @Test
    public void testSetFirst() {
        testPair.setFirst(777);
        assertThat(testPair.getFirst(), is(777));
    }

    /**
     * Testing set second element of the pair
     */
    @Test
    public void testSetSecond() {
        testPair.setSecond("abacabadabacaba");
        assertThat(testPair.getSecond(), is("abacabadabacaba"));
    }
}