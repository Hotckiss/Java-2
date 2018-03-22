package ru.spbau.kirilenko.hw3TicTac;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class that tests all methods of CurrentGameType class
 */
public class CurrentGameTypeTest {

    /**
     * Testing set new game type
     */
    @Test
    public void setType() {
        CurrentGameType.setType(2);
        assertEquals(2, CurrentGameType.getCurrentType());
    }

    /**
     * Testing get game type
     */
    @Test
    public void getCurrentType() {
        CurrentGameType.setType(1);
        assertEquals(1, CurrentGameType.getCurrentType());
    }
}