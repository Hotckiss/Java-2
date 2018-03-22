package ru.spbau.kirilenko.hw3TicTac.logic;

import java.util.ArrayList;
import java.util.Random;

import static ru.spbau.kirilenko.hw3TicTac.logic.CurrentTurn.*;
import static ru.spbau.kirilenko.hw3TicTac.logic.GameStatus.*;

/**
 * Class of game where AI makes random turns
 */
public class EasyGame extends AbstractGame {

    /**
     * {@inheritDoc}
     */
    @Override
    public MyPair<Integer, Integer> makeAITurn() {
        MyPair<Integer, Integer> move = null;

        if (status == PLAYING) {
            MyPair<Integer, Integer> canWin = immediateWin(IMMEDIATE_WIN_AI);
            Random random = new Random();
            ArrayList<MyPair<Integer, Integer>> freeBoxes = gameField.getEmptyFields();
            MyPair<Integer, Integer> toPut = freeBoxes.get((Math.abs(random.nextInt()) % freeBoxes.size()));

            if (canWin != null) {
                toPut = canWin;
            }

            gameField.putMark(toPut.getFirst(), toPut.getSecond(), getPlayerMark());
            currentTurn = TURN_X;
            status = updateStatus();
            move = new MyPair<>(toPut.getFirst(), toPut.getSecond());
        }

        return move;
    }
}
