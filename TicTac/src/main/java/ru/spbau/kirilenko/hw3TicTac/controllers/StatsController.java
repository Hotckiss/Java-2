package ru.spbau.kirilenko.hw3TicTac.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import ru.spbau.kirilenko.hw3TicTac.ScenesLoader;
import ru.spbau.kirilenko.hw3TicTac.statistics.Statistics;
import ru.spbau.kirilenko.hw3TicTac.statistics.StatsElement;

/**
 * Controller of played games statistics scene
 */
public class StatsController {

    @FXML private TableView<StatsElement> statsTable;

    /**
     * Fills stats table with saved data.
     */
    @FXML
    public void initialize() {
        ObservableList<StatsElement> data = FXCollections.observableArrayList(
                new StatsElement("Wins with easy AI", Statistics.getWinsEasy()),
                new StatsElement("Draws with easy AI", Statistics.getDrawsEasy()),
                new StatsElement("Loses with easy AI", Statistics.getLosesEasy()),
                new StatsElement("Wins with hard AI", Statistics.getWinsHard()),
                new StatsElement("Draws with hard AI", Statistics.getDrawsHard()),
                new StatsElement("Loses with hard AI", Statistics.getLosesHard()),
                new StatsElement("Wins for X in hotseat", Statistics.getWinsX()),
                new StatsElement("Wins for O in hotseat", Statistics.getWinsO()),
                new StatsElement("Draws in hotseat", Statistics.getDraws()));

        statsTable.setItems(data);
    }

    /**
     * Goes back in the menu hierarchy.
     */
    @FXML
    public void back() throws Exception {
        ScenesLoader.getStage().setScene(ScenesLoader.getMainMenuScene());
    }
}
