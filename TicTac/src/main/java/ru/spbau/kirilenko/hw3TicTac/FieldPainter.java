package ru.spbau.kirilenko.hw3TicTac;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * A class that allows to paint some elements of tic tac toe game
 */
public class FieldPainter {
    private static final int FIELD_WIDTH = 300;
    private static final int FIELD_HEIGHT = 300;
    private static final int BOX_WIDTH = FIELD_WIDTH / 3;
    private static final int BOX_HEIGHT = FIELD_HEIGHT / 3;
    private static final int SHIFT = 10;

    private static Canvas canvas;
    private static Text currentTurn;
    private static Text gameResult;

    /**
     * Main that initialize painter and associates canvas with it
     * @param field game field
     * @param status status bar of the game
     * @param result game result field
     */
    public static void init(Canvas field, Text status, Text result) {
        canvas = field;
        currentTurn = status;
        gameResult = result;
    }

    /**
     * Method that clears canvas and draw new grid for game
     */
    public static void initEmptyField() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, FIELD_WIDTH, FIELD_HEIGHT);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);

        gc.strokeLine(0, BOX_HEIGHT, FIELD_WIDTH, BOX_HEIGHT);
        gc.strokeLine(0, BOX_HEIGHT << 1, FIELD_WIDTH, BOX_HEIGHT << 1);
        gc.strokeLine(BOX_WIDTH, 0, BOX_WIDTH, FIELD_HEIGHT);
        gc.strokeLine(BOX_WIDTH << 1, 0, BOX_WIDTH << 1, FIELD_HEIGHT);
    }

    /**
     * Method that puts X in some square of the field
     * @param x row of X
     * @param y column of X
     */
    public static void putX(int x, int y) {
        if (x < 0 || x > 2 || y < 0 || y > 2) {
            return;
        }

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.BLUE);
        gc.setLineWidth(3);
        gc.strokeLine(x * BOX_WIDTH + SHIFT, y * BOX_HEIGHT + SHIFT, (x + 1) * BOX_WIDTH - SHIFT, (y + 1) * BOX_HEIGHT - SHIFT);
        gc.strokeLine((x + 1) * BOX_WIDTH - SHIFT, y * BOX_HEIGHT + SHIFT, x * BOX_WIDTH + SHIFT, (y + 1) * BOX_HEIGHT - SHIFT);
    }

    /**
     * Method that puts O in some square of the field
     * @param x row of O
     * @param y column of O
     */
    public static void putO(int x, int y) {
        if (x < 0 || x > 2 || y < 0 || y > 2) {
            return;
        }

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.RED);
        gc.setLineWidth(3);
        gc.strokeOval(x * BOX_WIDTH + SHIFT, y * BOX_HEIGHT + SHIFT, BOX_WIDTH - (SHIFT << 1), BOX_HEIGHT - (SHIFT << 1));
    }

    /**
     * Method that can change text in status bar
     * @param text new text in status bar
     */
    public static void setCurrentTurn(String text) {
        currentTurn.setText(text);
    }

    /**
     * Method that can change result of the game text
     * @param text new text of game result
     */
    public static void setResult(String text) {
        gameResult.setText(text);
    }
}
