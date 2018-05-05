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
        CurrentGameType.setType(GameTypes.PVP_GAME);
        assertEquals(GameTypes.PVP_GAME, CurrentGameType.getCurrentType());
    }

    /**
     * Testing get game type
     */
    @Test
    public void getCurrentType() {
        CurrentGameType.setType(GameTypes.HARD_GAME);
        assertEquals(GameTypes.HARD_GAME, CurrentGameType.getCurrentType());
    }
}