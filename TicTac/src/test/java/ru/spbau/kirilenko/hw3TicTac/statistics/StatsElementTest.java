package ru.spbau.kirilenko.hw3TicTac.statistics;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Class that tests all methods of stats element class
 */
public class StatsElementTest {
    private StatsElement se;

    /**
     * Init class before each test
     */
    @Before
    public void init() {
         se = new StatsElement("aaa", 1);
    }

    /**
     * Testing set key method
     */
    @Test
    public void testSetKey() {
        se.setKey("bbb");
        assertThat(se.getKey(), is("bbb"));
        se.setKey("ddd");
        assertThat(se.getKey(), is("ddd"));
    }

    /**
     * Testing set value method
     */
    @Test
    public void testSetValue() {
        se.setValue(7);
        assertThat(se.getValue(), is(7));
        se.setValue(9);
        assertThat(se.getValue(), is(9));
    }

    /**
     * Testing get key method
     */
    @Test
    public void testGetKey() {
        se.setKey("ccc");
        assertThat(se.getKey(), is("ccc"));
    }

    /**
     * Testing get value method
     */
    @Test
    public void testGetValue() {
        se.setValue(8);
        assertThat(se.getValue(), is(8));
    }
}