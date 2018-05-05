package ru.spbau.kirilenko.hw3TicTac.logic;

import javafx.util.Pair;

/**
 * Interface of ai player that decides move on given board in some game state
 */
public interface AiPlayer {
    /**
     * Method that returns AI player move by given game board and status
     * @param gameField game field with players moves
     * @param gameStatus current game status
     * @return AI player next move if it possible
     */
    Pair<Integer, Integer> move(GameField gameField, GameStatus gameStatus);
}
