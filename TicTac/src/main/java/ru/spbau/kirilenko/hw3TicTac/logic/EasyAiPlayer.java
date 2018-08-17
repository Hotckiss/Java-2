package ru.spbau.kirilenko.hw3TicTac.logic;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Random;

import static ru.spbau.kirilenko.hw3TicTac.logic.GameStatus.PLAYING;

public class EasyAiPlayer implements AiPlayer {
    /**
     * AI player that makes random moves
     * @param gameField game field with players moves
     * @param status current game status
     * @return AI player next move, null if it is not possible
     */
    @Override
    public Pair<Integer, Integer> move(GameField gameField, GameStatus status) {
        if (status != PLAYING) {
            return null;
        }

        Random random = new Random();
        ArrayList<Pair<Integer, Integer>> freeBoxes = gameField.getEmptyFields();

        return freeBoxes.get((Math.abs(random.nextInt()) % freeBoxes.size()));
    }
}
