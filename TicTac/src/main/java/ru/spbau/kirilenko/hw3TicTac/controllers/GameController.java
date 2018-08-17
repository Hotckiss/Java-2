package ru.spbau.kirilenko.hw3TicTac.controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Pair;
import ru.spbau.kirilenko.hw3TicTac.CurrentGameType;
import ru.spbau.kirilenko.hw3TicTac.GameTypes;
import ru.spbau.kirilenko.hw3TicTac.ScenesLoader;
import ru.spbau.kirilenko.hw3TicTac.logic.*;
import ru.spbau.kirilenko.hw3TicTac.statistics.Statistics;

import java.io.IOException;

import static ru.spbau.kirilenko.hw3TicTac.FieldPainter.FIELD_PAINTER_INSTANCE;
import static ru.spbau.kirilenko.hw3TicTac.logic.GameStatus.*;

/**
 * Controller of game field
 */
public class GameController {
    private Game game;

    @FXML public Canvas field;
    @FXML public Text status;
    @FXML public Text result;

    /**
     * Init game field to play
     */
    @FXML public void initialize() {
        FIELD_PAINTER_INSTANCE.init(field, status, result);
        FIELD_PAINTER_INSTANCE.initEmptyField();
        FIELD_PAINTER_INSTANCE.setCurrentTurn("X move");
        FIELD_PAINTER_INSTANCE.setResult("");
        if (CurrentGameType.getCurrentType() == GameTypes.EASY_GAME) {
            game = new Game(new EasyAiPlayer());
        } else {
            game = new Game(new HardAiPlayer());
        }
    }

    /**
     * Tries to make player turn in place that specified by mouse click
     * @param mouseEvent mouse event that occurred
     */
    @FXML
    public void mouseClick(MouseEvent mouseEvent) {

        int x = (int) (mouseEvent.getX() / 100.0);
        int y = (int) (mouseEvent.getY() / 100.0);

        if (CurrentGameType.getCurrentType() == GameTypes.PVP_GAME) {
            if (game.makePlayerTurn(x, y)) {
                if (game.getCurrentTurn() == CurrentTurn.TURN_O) {
                    FIELD_PAINTER_INSTANCE.putX(x, y);
                } else {
                    FIELD_PAINTER_INSTANCE.putO(x, y);
                }
                updateStatusBars();
            }
        } else {
            if (game.makePlayerTurn(x, y)) {
                FIELD_PAINTER_INSTANCE.putX(x, y);
                Pair<Integer, Integer> move = game.makeAITurn();

                if (move != null) {
                    FIELD_PAINTER_INSTANCE.putO(move.getKey(), move.getValue());
                }
                updateStatusBars();
            }
        }
    }

    /**
     * Method that restarts current game
     */
    @FXML
    public void restart() {
        initialize();
    }

    /**
     * Method thar returns back to previous menu
     * @throws IOException if error during loading scene occurred
     */
    @FXML
    public void back() throws IOException {
        if (CurrentGameType.getCurrentType() != GameTypes.PVP_GAME) {
            ScenesLoader.getStage().setScene(ScenesLoader.getSinglePlayerDifficulty());
        } else {
            ScenesLoader.getStage().setScene(ScenesLoader.getMainMenuScene());
        }
    }

    private void updateStatusBars() {
        if (game.getStatus() == WIN_X) {
            FIELD_PAINTER_INSTANCE.setResult("X wins!");

            if (CurrentGameType.getCurrentType() == GameTypes.EASY_GAME) {
                Statistics.incrementWinsEasy();
            } else if (CurrentGameType.getCurrentType() == GameTypes.HARD_GAME) {
                Statistics.incrementWinsHard();
            } else {
                Statistics.incrementWinsX();
            }
        }

        if (game.getStatus() == WIN_O) {
            FIELD_PAINTER_INSTANCE.setResult("O wins!");

            if (CurrentGameType.getCurrentType() == GameTypes.EASY_GAME) {
                Statistics.incrementLosesEasy();
            } else if (CurrentGameType.getCurrentType() == GameTypes.HARD_GAME) {
                Statistics.incrementLosesHard();
            } else {
                Statistics.incrementWinsO();
            }
        }

        if (game.getStatus() == DRAW) {
            FIELD_PAINTER_INSTANCE.setResult("Draw!");

            if (CurrentGameType.getCurrentType() == GameTypes.EASY_GAME) {
                Statistics.incrementDrawsEasy();
            } else if (CurrentGameType.getCurrentType() == GameTypes.HARD_GAME) {
                Statistics.incrementDrawsHard();
            } else {
                Statistics.incrementDraws();
            }
        }

        if (game.getStatus() == PLAYING) {
            if (game.getCurrentTurn() == CurrentTurn.TURN_X) {
                FIELD_PAINTER_INSTANCE.setCurrentTurn("X move");
            }

            if (game.getCurrentTurn() == CurrentTurn.TURN_O) {
                FIELD_PAINTER_INSTANCE.setCurrentTurn("O move");
            }
        }
    }
}
