package ru.spbau.kirilenko.hw3TicTac.logic;

import org.junit.Test;

import static org.junit.Assert.*;
import static ru.spbau.kirilenko.hw3TicTac.logic.CurrentTurn.TURN_O;
import static ru.spbau.kirilenko.hw3TicTac.logic.CurrentTurn.TURN_X;
import static ru.spbau.kirilenko.hw3TicTac.logic.GameStatus.PLAYING;

/**
 * Class that tests AI in easy game
 */
public class EasyGameTest {

    /**
     * Method that check correctness of making turns for AI
     */
    @Test
    public void makeAITurn() {
        EasyGame easyGame = new EasyGame();

        assertEquals(TURN_X, easyGame.getCurrentTurn());
        easyGame.makePlayerTurn(1, 1);

        assertEquals(TURN_O, easyGame.getCurrentTurn());
        easyGame.makeAITurn();

        assertEquals(TURN_X, easyGame.getCurrentTurn());
    }
}