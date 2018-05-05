package ru.spbau.kirilenko.hw3TicTac.logic;

import javafx.util.Pair;

import java.util.ArrayList;

import static ru.spbau.kirilenko.hw3TicTac.logic.GameStatus.WIN_O;
import static ru.spbau.kirilenko.hw3TicTac.logic.GameStatus.WIN_X;

public class HardAiPlayer implements AiPlayer {
    private static final int INF = 100000;
    private static final FieldMarks HUMAN_MARK = FieldMarks.X;
    private static final FieldMarks AI_MARK = FieldMarks.O;
    private static final int SITUATION_SCORE = 10;

    /**
     * AI player that makes good moves that can make AI player win
     * @param gameField game field with players moves
     * @param gameStatus current game status
     * @return AI player next move, null if it is not possible
     */
    @Override
    public Pair<Integer, Integer> move(GameField gameField, GameStatus gameStatus) {
        Pair<Integer, Pair<Integer, Integer>> best = analyze(gameField, false);
        if (best.getValue() == null) {
            return null;
        }

        return best.getValue();
    }

    /**
     * Minmax analysis of the game situation
     * @param gf current game field
     * @param isHuman which player makes turn now
     * @return best turn for player
     */
    private Pair<Integer, Pair<Integer, Integer>> analyze(GameField gf, boolean isHuman) {
        ArrayList<Pair<Integer, Integer>> freeBoxes = gf.getEmptyFields();

        if (freeBoxes.isEmpty()) {
            return new Pair<>(0, null);
        }

        if (Game.updateStatus(gf) == WIN_O) {
            return new Pair<>(SITUATION_SCORE, null);
        }

        if (Game.updateStatus(gf) == WIN_X) {
            return new Pair<>(-SITUATION_SCORE, null);
        }


        ArrayList<Pair<Integer, Pair<Integer, Integer>>> moves = new ArrayList<>();

        for (Pair move : freeBoxes) {
            if (isHuman) {
                moves.add(analyze(new GameField(gf, (int)move.getKey(), (int)move.getValue(), HUMAN_MARK), false));
            } else {
                moves.add(analyze(new GameField(gf, (int)move.getKey(), (int)move.getValue(), AI_MARK), true));
            }
        }

        int bestMove = 0;
        int best;
        if (isHuman) {
            best = INF;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).getKey() < best) {
                    best = moves.get(i).getKey();
                    bestMove = i;
                }
            }
        } else {
            best = -INF;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).getKey() > best) {
                    best = moves.get(i).getKey();
                    bestMove = i;
                }
            }
        }

        return new Pair<>(best, freeBoxes.get(bestMove));
    }
}
