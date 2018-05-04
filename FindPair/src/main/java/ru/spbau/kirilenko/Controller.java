package ru.spbau.kirilenko;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Controller which contain game logic and reacts on oser actions
 */
class Controller {
    private static final int MIN_CELL_SIZE = 30;
    @NotNull private final int[][] field;
    @NotNull private final Button[][] buttons;
    @Nullable private Button lastButtonClicked;
    private int left;

    /**
     * Constructs new game filled with random pairs of numbers
     *
     * @param fieldSize size of game field
     * @throws IllegalArgumentException if field size is incorrect (<=0 or odd)
     */
    @SuppressWarnings("WeakerAccess")
    public Controller(int fieldSize) throws IllegalArgumentException {
        if (fieldSize <= 0 || fieldSize % 2 != 0) {
            throw new IllegalArgumentException("Field size should be 2*k, 1 <= k <= 8");
        }

        left = fieldSize * fieldSize;
        List<Integer> numbers = generateRandomNumbers(left);
        field = new int[fieldSize][fieldSize];
        buttons = new Button[fieldSize][fieldSize];

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                field[i][j] = numbers.get(i * fieldSize + j);
                buttons[i][j] = constructButton(i, j);
            }
        }
    }

    /**
     * Returns field button with coordinates x and y if it exists
     *
     * @param x row of button
     * @param y column of button
     * @return field button with this coordinates
     * @throws IllegalArgumentException if button with such coordinates does not exists
     */
    @SuppressWarnings("WeakerAccess")
    @NotNull public Button getButton(int x, int y) throws IllegalArgumentException {
        if ((x < 0) || (y < 0) || (y >= buttons.length) || (x >= buttons.length)) {
            throw new IllegalArgumentException("Asking for not existing button");
        }
        return buttons[x][y];
    }

    /**
     * Generates list with N^2 / 2 pairs of random numbers (0<= number < N^2 / 2), which are shuffled
     *
     * @param size field area (N^2)
     * @return list of generated pairs
     */
    @NotNull
    private List<Integer> generateRandomNumbers(int size) {
        Random random = new Random();
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size / 2; i++) {
            int next = random.nextInt(size / 2);
            result.add(next);
            result.add(next);
        }

        for (int i = 0; i < 10; i++) {
            Collections.shuffle(result);
        }

        return result;
    }

    /**
     * Init field button and associates logic with it
     *
     * @param x row of button
     * @param y column of button
     * @return button that initialized for game
     */
    @NotNull
    private Button constructButton(int x, int y) {
        Button button = new Button();
        button.setFocusTraversable(false);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);
        button.setMinHeight(MIN_CELL_SIZE);
        button.setMinWidth(MIN_CELL_SIZE);

        button.setOnMouseClicked(event -> {
            button.setDisable(true);
            button.setText(String.valueOf(field[x][y]));
            if (lastButtonClicked == null) {
                lastButtonClicked = button;
            } else {
                if (lastButtonClicked.getText().equals(button.getText())) {
                    left -= 2;
                    if (left == 0) {
                        createWinAlert().showAndWait();
                    }
                } else {
                    generateDelay(lastButtonClicked, button).play();
                }
                lastButtonClicked = null;
            }
        });

        return button;
    }

    /**
     * Creates alert dialog that informs player about its win
     * @return created dialog
     */
    @NotNull private Alert createWinAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText("You win!");

        return alert;
    }

    /**
     * Method that disables last two not matched buttons after 0.5 sec
     * @param lastButton pre-last pressed button
     * @param currentButton last pressed button
     * @return generated delay
     */
    @NotNull private Timeline generateDelay(final Button lastButton, final Button currentButton) {
        return new Timeline(new KeyFrame(
                Duration.millis(500),
                ae -> { lastButton.setText("");
                        lastButton.setDisable(false);
                        currentButton.setText("");
                        currentButton.setDisable(false);
                }
        ));
    }
}
