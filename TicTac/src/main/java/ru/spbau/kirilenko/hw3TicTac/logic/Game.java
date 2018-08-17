package ru.spbau.kirilenko.hw3TicTac.logic;

import javafx.util.Pair;

import static ru.spbau.kirilenko.hw3TicTac.logic.CurrentTurn.*;
import static ru.spbau.kirilenko.hw3TicTac.logic.FieldMarks.O;
import static ru.spbau.kirilenko.hw3TicTac.logic.FieldMarks.X;
import static ru.spbau.kirilenko.hw3TicTac.logic.GameStatus.*;

/**
 * Class that represents game logic except implementation of AI make turn function
 */
public class Game {
    private GameField gameField = new GameField();
    private GameStatus status = PLAYING;
    private CurrentTurn currentTurn = TURN_X;
    private AiPlayer aiPlayer;

    /**
     * Constructs complete game with given AI
     * @param player AI player
     */
    public Game(AiPlayer player) {
        this.aiPlayer = player;
    }

    /**
     * Method that returns position where AI player wants to make turn
     * @return position of AI move
     */
    public Pair<Integer, Integer> makeAITurn() {
        Pair<Integer, Integer> best = aiPlayer.move(gameField, status);
        if (best != null) {
            gameField.putMark(best.getKey(), best.getValue(), getPlayerMark());
            currentTurn = TURN_X;
            status = updateStatus();
        }

        return best;
    };

    /**
     * Method that makes turn in specific position of field for player
     * @param x x coordinate of move position
     * @param y y coordinate of move position
     * @return true if turn was made correctly false otherwise
     */
    public boolean makePlayerTurn(int x, int y) {
        if (status != PLAYING) {
            return false;
        }

        if (!gameField.isAccessible(x, y)) {
            return false;
        }

        gameField.putMark(x, y, getPlayerMark());

        if (currentTurn == TURN_O) {
            currentTurn = TURN_X;
        } else {
            currentTurn = TURN_O;
        }

        status = updateStatus();

        return true;
    }

    /**
     * Method that returns current game status
     * @return current game status
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * Method that returns current game turn
     * @return current game turn
     */
    public CurrentTurn getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Method that updates current game status
     * @return updated game status
     */
    private GameStatus updateStatus() {
        return updateStatus(gameField);
    }

    /**
     * Method that updates some game field status
     * @return updated game status
     */
    protected static GameStatus updateStatus(GameField gf) {
        for (int i = 0; i < 3; i++) {
            FieldMarks rowMark = gf.getRowWinner(i);
            FieldMarks colMark = gf.getColWinner(i);

            if ((rowMark == X) || (colMark == X)) {
                return WIN_X;
            } else if ((rowMark == O) || (colMark == O)) {
                return WIN_O;
            }
        }
        FieldMarks mainDiag = gf.getMainDiagWinner();
        FieldMarks otherDiag = gf.getOtherDiagWinner();

        if ((mainDiag ==  X) || (otherDiag ==  X)) {
            return WIN_X;
        } else if ((mainDiag == O) || (otherDiag == O)) {
            return WIN_O;
        }

        if (gf.isFull()) {
            return DRAW;
        }

        return PLAYING;
    }

    /**
     * Method that returns current player mark
     * @return mark of current player
     */
    private FieldMarks getPlayerMark() {
        if (currentTurn == TURN_X) {
            return X;
        }
        return O;
    }
}
