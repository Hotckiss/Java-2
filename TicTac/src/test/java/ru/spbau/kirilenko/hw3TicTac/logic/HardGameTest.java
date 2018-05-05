package ru.spbau.kirilenko.hw3TicTac.logic;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static ru.spbau.kirilenko.hw3TicTac.logic.CurrentTurn.TURN_O;
import static ru.spbau.kirilenko.hw3TicTac.logic.CurrentTurn.TURN_X;
import static ru.spbau.kirilenko.hw3TicTac.logic.GameStatus.PLAYING;
import static ru.spbau.kirilenko.hw3TicTac.logic.GameStatus.WIN_O;
import static ru.spbau.kirilenko.hw3TicTac.logic.GameStatus.WIN_X;

/**
 * Class that tests AI in hard game and additional helpful methods
 */
public class HardGameTest {
    private Game hardGame;

    /**
     * Init hard game
     */
    @Before
    public void init() {
        hardGame = new Game(new HardAiPlayer());
    }

    /**
     * Method that check correctness of current turn
     */
    @Test
    public void testGetCurrentTurn() {
        assertEquals(TURN_X, hardGame.getCurrentTurn());
    }

    /**
     * Method that check correctness of game status
     */
    @Test
    public void testGetStatus() {
        assertEquals(PLAYING, hardGame.getStatus());
    }

    /**
     * Method that check correctness of making turns for human
     */
    @Test
    public void testMakePlayerTurn() {
        assertEquals(TURN_X, hardGame.getCurrentTurn());

        hardGame.makePlayerTurn(0, 0);

        assertEquals(TURN_O, hardGame.getCurrentTurn());
        assertEquals(PLAYING, hardGame.getStatus());

        hardGame.makePlayerTurn(0, 1);

        assertEquals(TURN_X, hardGame.getCurrentTurn());
        assertEquals(PLAYING, hardGame.getStatus());

        hardGame.makePlayerTurn(1, 1);

        assertEquals(TURN_O, hardGame.getCurrentTurn());
        assertEquals(PLAYING, hardGame.getStatus());

        hardGame.makePlayerTurn(0, 2);

        assertEquals(TURN_X, hardGame.getCurrentTurn());
        assertEquals(PLAYING, hardGame.getStatus());

        hardGame.makePlayerTurn(2, 2);

        assertEquals(TURN_O, hardGame.getCurrentTurn());
        assertEquals(WIN_X, hardGame.getStatus());
    }

    /**
     * Method that check correctness of making turns for AI
     */
    @Test
    public void testMakeAITurn() {
        assertEquals(TURN_X, hardGame.getCurrentTurn());

        hardGame.makePlayerTurn(1, 1);

        Pair<Integer, Integer> mp = hardGame.makeAITurn();
        assertThat(mp.getKey(), is(0));
        assertThat(mp.getValue(), is(0));

        hardGame.makePlayerTurn(2, 0);

        mp = hardGame.makeAITurn();
        assertThat(mp.getKey(), is(0));
        assertThat(mp.getValue(), is(2));

        hardGame.makePlayerTurn(2, 1);

        mp = hardGame.makeAITurn();
        assertThat(mp.getKey(), is(0));
        assertThat(mp.getValue(), is(1));

        assertEquals(WIN_O, hardGame.getStatus());
    }
}