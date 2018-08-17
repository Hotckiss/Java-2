package ru.spbau.kirilenko.hw3TicTac;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class that allows to change game scenes
 */
public class ScenesLoader {
    private static final int WIDTH = 380;
    private static final int HEIGHT = 380;

    private static Stage stage;

    /**
     * Method that allows to set primary stage to load all other scenes
     * @param newStage stage to store
     */
    public static void setStage(Stage newStage) {
        stage = newStage;
    }

    /**
     * Method that returns primary game stage
     * @return primary game stage
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Method that loads main menu scene to window
     * @return new scene with main menu
     * @throws IOException if error during loading scene occurred
     */
    public static Scene getMainMenuScene() throws IOException {
        return new Scene(FXMLLoader.load(ScenesLoader.class.getResource("/LayoutMain.fxml")), WIDTH, HEIGHT);
    }

    /**
     * Method that loads difficulty choosing scene to window
     * @return new scene with difficulty choosing menu
     * @throws IOException if error during loading scene occurred
     */
    public static Scene getSinglePlayerDifficulty() throws IOException {
        return new Scene(FXMLLoader.load(ScenesLoader.class.getResource("/LayoutSinglePlayer.fxml")), WIDTH, HEIGHT);
    }

    /**
     * Method that loads game scene to window
     * @return new scene with game
     * @throws IOException if error during loading scene occurred
     */
    public static Scene getGameScene() throws IOException {
        return new Scene(FXMLLoader.load(ScenesLoader.class.getResource("/LayoutGame.fxml")), WIDTH, HEIGHT);
    }

    /**
     * Method that loads stats scene to window
     * @return new scene with stats
     * @throws IOException if error during loading scene occurred
     */
    public static Scene getStatsScene() throws IOException {
        return new Scene(FXMLLoader.load(ScenesLoader.class.getResource("/LayoutStats.fxml")), WIDTH, HEIGHT);
    }
}
