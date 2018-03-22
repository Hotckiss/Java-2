package ru.spbau.kirilenko.hw3TicTac.logic;

import static ru.spbau.kirilenko.hw3TicTac.logic.CurrentTurn.*;
import static ru.spbau.kirilenko.hw3TicTac.logic.GameStatus.*;

/**
 * Class that represents game logic except implementation of AI make turn function
 */
@SuppressWarnings("WeakerAccess")
public abstract class AbstractGame {
    protected static final int WIN_X_PRODUCT = 1;
    protected static final int WIN_O_PRODUCT = 64;
    protected static final int IMMEDIATE_WIN_AI = 8;

    protected GameField gameField = new GameField();
    protected GameStatus status = PLAYING;
    protected CurrentTurn currentTurn = TURN_X;

    /**
     * Method that returns position where AI player wants to make turn
     * @return position of AI move
     */
    public abstract MyPair<Integer, Integer> makeAITurn();

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
     * Method that checks whether player can win in one move
     * @param winningSum team winning sum
     * @return true if current team can win in one move false otherwise
     */
    protected MyPair<Integer, Integer> immediateWin(int winningSum) {
        for (int i = 0; i < 3; i++) {
            if (gameField.getRowSum(i) == winningSum) {
                for (int j = 0; j < 3; j++) {
                    if (gameField.getMark(i, j) == 0) {
                        return new MyPair<>(i, j);
                    }
                }
            }

            if (gameField.getColSum(i) == winningSum) {
                for (int j = 0; j < 3; j++) {
                    if (gameField.getMark(j, i) == 0) {
                        return new MyPair<>(j, i);
                    }
                }
            }
        }

        if (gameField.getMainDiagSum() == winningSum) {
            for (int i = 0; i < 3; i++) {
                if (gameField.getMark(i, i) == 0) {
                    return new MyPair<>(i, i);
                }
            }
        }

        if (gameField.getOtherDiagSum() == winningSum) {
            for (int i = 0; i < 3; i++) {
                if (gameField.getMark(i, 2 - i) == 0) {
                    return new MyPair<>(i, 2 - i);
                }
            }
        }

        return null;
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
    protected GameStatus updateStatus() {
        return updateStatus(gameField);
    }

    /**
     * Method that updates some game field status
     * @return updated game status
     */
    protected GameStatus updateStatus(GameField gf) {
        for (int i = 0; i < 3; i++) {
            int rowProduct = gf.getRowProduct(i);
            int colProduct = gf.getColProduct(i);

            if ((rowProduct == WIN_X_PRODUCT) || (colProduct == WIN_X_PRODUCT)) {
                return WIN_X;
            } else if ((rowProduct == WIN_O_PRODUCT) || (colProduct == WIN_O_PRODUCT)) {
                return WIN_O;
            }
        }
        int mainDiag = gf.getMainDiagProduct();
        int otherDiag = gf.getOtherDiagProduct();

        if ((mainDiag == WIN_X_PRODUCT) || (otherDiag == WIN_X_PRODUCT)) {
            return WIN_X;
        } else if ((mainDiag == WIN_O_PRODUCT) || (otherDiag == WIN_O_PRODUCT)) {
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
    protected int getPlayerMark() {
        if (currentTurn == TURN_X) {
            return 1;
        }
        return 4;
    }
}
