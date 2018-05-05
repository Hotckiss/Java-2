package ru.spbau.kirilenko.hw3TicTac.logic;

import javafx.util.Pair;

import java.util.ArrayList;

import static ru.spbau.kirilenko.hw3TicTac.logic.FieldMarks.*;

/**
 * Class that contains methods to work with 3x3 array of integers
 */
@SuppressWarnings("WeakerAccess")
public class GameField {
    private FieldMarks[][] field = new FieldMarks[3][3];

    /**
     * Default constructor with standard array initialization
     */
    public GameField() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = EMPTY;
            }
        }
    }

    /**
     * Constructor with all data from other field, except one element
     * @param gf field with init data
     * @param x x coordinate of element to change value
     * @param y y coordinate of element to change value
     * @param value new element value
     */
    public GameField(GameField gf, int x, int y, FieldMarks value) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = gf.getMark(i, j);
            }
        }

        field[x][y] = value;
    }

    /**
     * Method that puts value in some position of field
     * @param x x coordinate of element
     * @param y y coordinate of element
     * @param value new element value
     */
    public void putMark(int x, int y, FieldMarks value) {
        if (x < 0 || x > 2 || y < 0 || y > 2) {
            return;
        }

        field[x][y] = value;
    }

    /**
     * Method that gets value from some position of field
     * @param x x coordinate of element
     * @param y y coordinate of element
     * @return element value
     * @throws IllegalArgumentException is cell does not exist
     */
    public FieldMarks getMark(int x, int y) throws IllegalArgumentException {
        if (x < 0 || x > 2 || y < 0 || y > 2) {
            throw new IllegalArgumentException("Cell does not exist");
        }

        return field[x][y];
    }

    /**
     * Method that checks that element value is accessible (is zero)
     * @param x x coordinate of element
     * @param y y coordinate of element
     * @return true if element is accessible false otherwise
     */
    public boolean isAccessible(int x, int y) {
        return x >= 0 && x <= 2 && y >= 0 && y <= 2 && field[x][y] == FieldMarks.EMPTY;
    }

    /**
     * Method thar returns all accessible elements of the field
     * @return array list of all accessible elements
     */
    public ArrayList<Pair<Integer, Integer>> getEmptyFields() {
        ArrayList<Pair<Integer, Integer>> result = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (isAccessible(i, j)) {
                    result.add(new Pair<>(i, j));
                }
            }
        }

        return result;
    }

    /**
     * Check that all values are not accessible
     * @return true if all values are not accessible false otherwise
     */
    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j] == FieldMarks.EMPTY) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Method that converts field to string
     * @return string representation of game field
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                stringBuilder.append(field[i][j].ordinal());
            }
        }

        return stringBuilder.toString();
    }

    /**
     * Returns value of specific field row, EMPTY if they are different
     * @param row row to get value
     * @return row same value if it exists
     */
    public FieldMarks getRowWinner(int row) {
        if (field[row][0] == X && field[row][1] == X && field[row][2] == X) {
            return X;
        } else if (field[row][0] == O && field[row][1] == O && field[row][2] == O) {
            return O;
        } else {
            return EMPTY;
        }
    }

    /**
     * Returns value of specific field col, EMPTY if they are different
     * @param col col to get value
     * @return col same value if it exists
     */
    public FieldMarks getColWinner(int col) {
        if (field[0][col] == X && field[1][col] == X && field[2][col] == X) {
            return X;
        } else if (field[0][col] == O && field[1][col] == O && field[2][col] == O) {
            return O;
        } else {
            return EMPTY;
        }
    }

    /**
     * Returns value of field main diagonal, EMPTY if they are different
     * @return main diagonal same value if it exists
     */
    public FieldMarks getMainDiagWinner() {
        if (field[0][0] == X && field[1][1] == X && field[2][2] == X) {
            return X;
        } else if (field[0][0] == O && field[1][1] == O && field[2][2] == O) {
            return O;
        } else {
            return EMPTY;
        }
    }

    /**
     * Returns value of field other diagonal, EMPTY if they are different
     * @return other diagonal same value if it exists
     */
    public FieldMarks getOtherDiagWinner() {
        if (field[0][2] == X && field[1][1] == X && field[2][0] == X) {
            return X;
        } else if (field[0][2] == O && field[1][1] == O && field[2][0] == O) {
            return O;
        } else {
            return EMPTY;
        }
    }
}
