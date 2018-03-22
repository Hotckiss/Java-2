package ru.spbau.kirilenko.hw3TicTac.logic;

import java.util.ArrayList;

import static ru.spbau.kirilenko.hw3TicTac.logic.CurrentTurn.TURN_X;
import static ru.spbau.kirilenko.hw3TicTac.logic.GameStatus.*;

/**
 * Class of game where AI makes good turns and win is difficult
 * YOU CAN WIN IT IF YOU MAKE THE WORST TURN(middle of the not central row/col) AT FIRST!
 * Example:
 * ...    x..    xx.    xx.    xx.
 * ..x -> ..x -> ..x -> .xx -> .xx
 * ...    ...    ...    ...    ..x
 */
public class HardGame extends AbstractGame {
    private static final int INF = 100000;
    private static final int HUMAN_MARK = 1;
    private static final int AI_MARK = 4;
    private static final int SITUATION_SCORE = 10;

    /**
     * {@inheritDoc}
     */
    @Override
    public MyPair<Integer, Integer> makeAITurn() {
        MyPair<Integer, MyPair<Integer, Integer>> best = analyze(gameField, false);
        if (best.getSecond() == null) {
            return null;
        }

        gameField.putMark(best.getSecond().getFirst(), best.getSecond().getSecond(), getPlayerMark());
        currentTurn = TURN_X;
        status = updateStatus();
        return best.getSecond();
    }

    /**
     * Minmax analysis of the game situation
     * @param gf current game field
     * @param isHuman which player makes turn now
     * @return best turn for player
     */
    private MyPair<Integer, MyPair<Integer, Integer>> analyze(GameField gf, boolean isHuman) {
        ArrayList<MyPair<Integer, Integer>> freeBoxes = gf.getEmptyFields();

        if (freeBoxes.isEmpty()) {
            return new MyPair<>(0, null);
        }

        if (updateStatus(gf) == WIN_O) {
            return new MyPair<>(SITUATION_SCORE, null);
        }

        if (updateStatus(gf) == WIN_X) {
            return new MyPair<>(-SITUATION_SCORE, null);
        }


        ArrayList<MyPair<Integer, MyPair<Integer, Integer>>> moves = new ArrayList<>();

        for (MyPair move : freeBoxes) {
            if (isHuman) {
                moves.add(analyze(new GameField(gf, (int)move.getFirst(), (int)move.getSecond(), HUMAN_MARK), false));
            } else {
                moves.add(analyze(new GameField(gf, (int)move.getFirst(), (int)move.getSecond(), AI_MARK), true));
            }
        }

        int bestMove = 0;
        int best;
        if (isHuman) {
            best = INF;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).getFirst() < best) {
                    best = moves.get(i).getFirst();
                    bestMove = i;
                }
            }
        } else {
            best = -INF;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).getFirst() > best) {
                    best = moves.get(i).getFirst();
                    bestMove = i;
                }
            }
        }

        return new MyPair<>(best, freeBoxes.get(bestMove));
    }
}
