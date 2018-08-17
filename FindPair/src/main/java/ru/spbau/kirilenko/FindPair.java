package ru.spbau.kirilenko;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

/**
 * Main application class which parse field size, make it correct and runs new game
 */
public class FindPair extends Application {
    private static final int MIN_CELL_SIZE = 30;
    private static final int INIT_ADDITIONAL_SIZE = 100;
    private static final int MIN_ADDITIONAL_SIZE = 50;

    private static int fieldSize;

    /**
     * Main method which runs new game
     *
     * @param args argument with field size
     */
    public static void main(@NotNull String[] args) {
        fieldSize = generateFieldSize(args);
        Application.launch(args);
    }

    /**
     * Method that init field size, protected for testing
     *
     * @param args user command line arguments
     * @return correct field size
     */
    @SuppressWarnings("WeakerAccess")
    protected static int generateFieldSize(String args[]) {
        int result;

        if (args.length != 0) {
            try {
                result = Integer.parseInt(args[0]);
            } catch (NumberFormatException nfe) {
                result = 4;
            }

            /*
              Make input size >= 0
             */
            if(result < 0) {
                result = Math.abs(result);
            }

            /*
             * Make field size 2*k, k >= 0
             */
            if(result % 2 != 0) {
                result++;
            }

            /*
             * Make field size 2*k, k >= 1
             */
            if (result == 0) {
                result += 2;
            }

            /*
             * Make field size 2 * k, 1 <= k <= 8
             */
            result = Math.min(16, result);
        } else {
            result = 4;
        }

        return result;
    }
    /**
     * Method that initialize window size, icon and title
     * After that generates field for game
     */
    @Override
    public void start(@NotNull Stage primaryStage) {
        primaryStage.setTitle("Find Pair");
        primaryStage.setMinHeight(fieldSize * MIN_CELL_SIZE + MIN_ADDITIONAL_SIZE);
        primaryStage.setMinWidth(fieldSize * MIN_CELL_SIZE + MIN_ADDITIONAL_SIZE);
        primaryStage.getIcons().add(new Image("/logo.jpg"));
        primaryStage.setScene(generateField(fieldSize));
        primaryStage.show();
    }

    /**
     * Method that fills window with buttons associated with controller buttons
     *
     * @param size field size
     * @return scene filled with N^2 buttons
     */
    @NotNull private Scene generateField(int size) {
        Controller controller = new Controller(size);
        HBox[] rows = new HBox[size];
        for (int i = 0; i < size; i++) {
            rows[i] = new HBox();
            for (int j = 0; j < size; j++) {
                Button button = controller.getButton(i, j);
                HBox.setHgrow(button, Priority.ALWAYS);
                VBox.setVgrow(button, Priority.ALWAYS);
                button.prefWidthProperty().bind(rows[i].widthProperty().divide(size));
                rows[i].getChildren().add(button);
            }
        }

        VBox field = new VBox();
        for (HBox row : rows) {
            VBox.setVgrow(row, Priority.ALWAYS);
            field.getChildren().add(row);
        }

        return new Scene(field, fieldSize * MIN_CELL_SIZE + INIT_ADDITIONAL_SIZE, fieldSize * MIN_CELL_SIZE + INIT_ADDITIONAL_SIZE);
    }
}
