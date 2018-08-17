package ru.spbau.kirilenko.hw4SimpleFTP;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Class that stores application stage and switches between different windows
 */
public class StageStorage {
    @SuppressWarnings("WeakerAccess")
    public static final StageStorage INSTANCE = new StageStorage();
    private Stage stage;

    /**
     * Sets application stage
     * @param newStage application stage
     */
    public void setStage(@NotNull Stage newStage) {
        stage = newStage;
    }

    /**
     * Returns current stage
     * @return stage that currently stored
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Switches to the main menu window
     * @throws IOException if error during loading scene occurred
     */
    public void getMainMenuScene() throws IOException {
        stage.setScene(new Scene(FXMLLoader.load(StageStorage.class.getResource("/Menu.fxml")), 610, 410));
    }

    /**
     * Switches to the server folder window
     * @throws IOException if error during loading scene occurred
     */
    public void getServerScene() throws IOException {
        stage.setScene(new Scene(FXMLLoader.load(StageStorage.class.getResource("/GUIClient.fxml")), 610, 410));
    }

    private StageStorage() {
    }
}
