package ru.spbau.kirilenko.hw3TicTac.controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import ru.spbau.kirilenko.hw3TicTac.CurrentGameType;
import ru.spbau.kirilenko.hw3TicTac.FieldPainter;
import ru.spbau.kirilenko.hw3TicTac.ScenesLoader;
import ru.spbau.kirilenko.hw3TicTac.logic.*;
import ru.spbau.kirilenko.hw3TicTac.statistics.Statistics;

import java.io.IOException;

import static ru.spbau.kirilenko.hw3TicTac.logic.GameStatus.*;

/**
 * Controller of game field
 */
public class GameController {
    private AbstractGame game;

    @FXML public Canvas field;
    @FXML public Text status;
    @FXML public Text result;

    /**
     * Create AI that specified by game settings
     */
    public GameController() {
        if (CurrentGameType.getCurrentType() == 0) {
            game = new EasyGame();
        } else {
            game = new HardGame();
        }
    }

    /**
     * Init game field to play
     */
    @FXML public void initialize() {
        FieldPainter.init(field, status, result);
        FieldPainter.initEmptyField();
        FieldPainter.setCurrentTurn("X move");
        FieldPainter.setResult("");
        if (CurrentGameType.getCurrentType() == 0) {
            game = new EasyGame();
        } else {
            game = new HardGame();
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

        if (CurrentGameType.getCurrentType() == 2) {
            if (game.makePlayerTurn(x, y)) {
                if (game.getCurrentTurn() == CurrentTurn.TURN_O) {
                    FieldPainter.putX(x, y);
                } else {
                    FieldPainter.putO(x, y);
                }
                updateStatusBars();
            }
        } else {
            if (game.makePlayerTurn(x, y)) {
                FieldPainter.putX(x, y);
                MyPair<Integer, Integer> move = game.makeAITurn();

                if (move != null) {
                    FieldPainter.putO(move.getFirst(), move.getSecond());
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
        if (CurrentGameType.getCurrentType() != 2) {
            ScenesLoader.getStage().setScene(ScenesLoader.getSinglePlayerDifficulty());
        } else {
            ScenesLoader.getStage().setScene(ScenesLoader.getMainMenuScene());
        }
    }

    private void updateStatusBars() {
        if (game.getStatus() == WIN_X) {
            FieldPainter.setResult("X wins!");

            if (CurrentGameType.getCurrentType() == 0) {
                Statistics.incrementWinsEasy();
            } else if (CurrentGameType.getCurrentType() == 1) {
                Statistics.incrementWinsHard();
            } else {
                Statistics.incrementWinsX();
            }
        }

        if (game.getStatus() == WIN_O) {
            FieldPainter.setResult("O wins!");

            if (CurrentGameType.getCurrentType() == 0) {
                Statistics.incrementLosesEasy();
            } else if (CurrentGameType.getCurrentType() == 1) {
                Statistics.incrementLosesHard();
            } else {
                Statistics.incrementWinsO();
            }
        }

        if (game.getStatus() == DRAW) {
            FieldPainter.setResult("Draw!");

            if (CurrentGameType.getCurrentType() == 0) {
                Statistics.incrementDrawsEasy();
            } else if (CurrentGameType.getCurrentType() == 1) {
                Statistics.incrementDrawsHard();
            } else {
                Statistics.incrementDraws();
            }
        }

        if (game.getStatus() == PLAYING) {
            if (game.getCurrentTurn() == CurrentTurn.TURN_X) {
                FieldPainter.setCurrentTurn("X move");
            }

            if (game.getCurrentTurn() == CurrentTurn.TURN_O) {
                FieldPainter.setCurrentTurn("O move");
            }
        }
    }
}
