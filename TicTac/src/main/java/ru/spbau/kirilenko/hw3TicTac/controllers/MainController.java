package ru.spbau.kirilenko.hw3TicTac.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import ru.spbau.kirilenko.hw3TicTac.CurrentGameType;
import ru.spbau.kirilenko.hw3TicTac.ScenesLoader;

import java.io.IOException;

/**
 * Controller of the main application
 */
public class MainController {

    /**
     * Action that starts new game with AI
     * @throws IOException if any error during loading scene occurred
     */
    @FXML
    public void initSinglePlayerGame() throws IOException {
        ScenesLoader.getStage().setScene(ScenesLoader.getSinglePlayerDifficulty());
    }

    /**
     * Action that starts new hotseat game
     * @throws IOException if any error during loading scene occurred
     */
    @FXML
    public void initHotSeatGame() throws IOException {
        CurrentGameType.setType(2);
        ScenesLoader.getStage().setScene(ScenesLoader.getGameScene());

    }

    /**
     * Action that shows current session statistics
     * @throws IOException if any error during loading scene occurred
     */
    @FXML
    public void loadStats() throws IOException {
        ScenesLoader.getStage().setScene(ScenesLoader.getStatsScene());
    }

    /**
     * Action that terminates application
     */
    @FXML
    public void exit() {
        Platform.exit();
    }
}
