package ru.spbau.kirilenko.hw3TicTac.controllers;

import javafx.fxml.FXML;
import ru.spbau.kirilenko.hw3TicTac.CurrentGameType;
import ru.spbau.kirilenko.hw3TicTac.ScenesLoader;

import java.io.IOException;

/**
 * Controller of choosing single player difficulty scene
 */
public class SinglePlayerDifficultyController {

    /**
     * Action that starts new easy game
     * @throws IOException if any error during loading scene occurred
     */
    @FXML
    public void startEasyGame() throws IOException {
        CurrentGameType.setType(0);
        ScenesLoader.getStage().setScene(ScenesLoader.getGameScene());
    }

    /**
     * Action that starts new hard game
     * @throws IOException if any error during loading scene occurred
     */
    @FXML
    public void startHardGame() throws IOException {
        CurrentGameType.setType(1);
        ScenesLoader.getStage().setScene(ScenesLoader.getGameScene());

    }

    /**
     * Action that returns back to main menu
     * @throws IOException if any error during loading scene occurred
     */
    @FXML
    public void backToMain() throws IOException {
        ScenesLoader.getStage().setScene(ScenesLoader.getMainMenuScene());
    }
}
