package ru.spbau.kirilenko.hw3TicTac.logic;

import java.util.ArrayList;

/**
 * Class that contains methods to work with 3x3 array of integers
 */
@SuppressWarnings("WeakerAccess")
public class GameField {
    private int[][] field = new int[3][3];

    /**
     * Default constructor with standard array initialization
     */
    public GameField() {
    }

    /**
     * Constructor with all data from other field, except one element
     * @param gf field with init data
     * @param x x coordinate of element to change value
     * @param y y coordinate of element to change value
     * @param value new element value
     */
    public GameField(GameField gf, int x, int y, int value) {
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
    public void putMark(int x, int y, int value) {
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
     */
    public int getMark(int x, int y) {
        if (x < 0 || x > 2 || y < 0 || y > 2) {
            return -1;
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
        return x >= 0 && x <= 2 && y >= 0 && y <= 2 && field[x][y] == 0;
    }

    /**
     * Method thar returns all accessible elements of the field
     * @return array list of all accessible elements
     */
    public ArrayList<MyPair<Integer, Integer>> getEmptyFields() {
        ArrayList<MyPair<Integer, Integer>> result = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (isAccessible(i, j)) {
                    result.add(new MyPair<>(i, j));
                }
            }
        }

        return result;
    }

    /**
     * Returns value product of specific field row
     * @param row row to get product
     * @return row product
     */
    public int getRowProduct(int row) {
        if (row < 0 || row > 2) {
            return -1;
        }

        return field[row][0] * field[row][1] * field[row][2];
    }

    /**
     * Returns value product of specific field col
     * @param col col to get product
     * @return col product
     */
    public int getColProduct(int col) {
        if (col < 0 || col > 2) {
            return -1;
        }

        return field[0][col] * field[1][col] * field[2][col];
    }

    /**
     * Returns value product of main diagonal
     * @return main diagonal product
     */
    public int getMainDiagProduct() {
        return field[0][0] * field[1][1] * field[2][2];
    }

    /**
     * Returns value product of other diagonal
     * @return other diagonal product
     */
    public int getOtherDiagProduct() {
        return field[0][2] * field[1][1] * field[2][0];
    }

    /**
     * Check that all values are not accessible
     * @return true if all values are not accessible false otherwise
     */
    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j] == 0) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns value sum of specific field row
     * @param row row to get sum
     * @return row sum
     */
    public int getRowSum(int row) {
        if (row < 0 || row > 2) {
            return -1;
        }

        return field[row][0] + field[row][1] + field[row][2];
    }

    /**
     * Returns value sum of specific field col
     * @param col col to get sum
     * @return col sum
     */
    public int getColSum(int col) {
        if (col < 0 || col > 2) {
            return -1;
        }

        return field[0][col] + field[1][col] + field[2][col];
    }

    /**
     * Returns value sum of main diagonal
     * @return main diagonal sum
     */
    public int getMainDiagSum() {
        return field[0][0] + field[1][1] + field[2][2];
    }

    /**
     * Returns value sum of other diagonal
     * @return other diagonal sum
     */
    public int getOtherDiagSum() {
        return field[0][2] + field[1][1] + field[2][0];
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
                stringBuilder.append(field[i][j]);
            }
        }

        return stringBuilder.toString();
    }
}
