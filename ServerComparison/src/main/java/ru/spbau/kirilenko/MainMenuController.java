package ru.spbau.kirilenko;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import lombok.AllArgsConstructor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

public class MainMenuController {
    private static final Logger logger = Logger.getLogger("UILogger");
    private boolean error = false;
    @FXML private ChoiceBox architecture;
    @FXML private TextField valueX;
    @FXML private TextField lowerBound;
    @FXML private TextField upperBound;
    @FXML private ChoiceBox variableToChange;
    @FXML private TextField stepBound;
    @FXML private TextField valueN;
    @FXML private TextField valueM;
    @FXML private TextField valueDelta;
    private LineChart<Number, Number> chart;
    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();

    @FXML public void initialize() {
        architecture.getSelectionModel().selectFirst();
        variableToChange.getSelectionModel().selectFirst();
        lowerBound.setText("10000");
        upperBound.setText("20000");
        stepBound.setText("2500");
        valueX.setText("10");
        valueN.setText("15000");
        valueM.setText("10");
        valueDelta.setText("100");
    }

    public void runBench(ActionEvent actionEvent) {
        ArchitectureType selectedArch = getArchType();
        int valX = parseValue(valueX, 1);
        BoundedValue bv = getVariable();

        int arraysSize;
        int clientsOnline;
        int delay;

        switch (bv.getType()) {
            case NUMBERS_IN_ARRAY:
                clientsOnline = parseValue(valueM, 1);
                delay = parseValue(valueDelta, 100);
                run(new BenchmarkSettings(selectedArch, valX, bv, 0, clientsOnline, delay));
                break;
            case CLIENTS_ONLINE:
                arraysSize = parseValue(valueN, 10);
                delay = parseValue(valueDelta, 100);
                run(new BenchmarkSettings(selectedArch, valX, bv, arraysSize, 0, delay));
                break;
            case QUERY_DELAY:
                arraysSize = parseValue(valueN, 10);
                clientsOnline = parseValue(valueM, 1);
                run(new BenchmarkSettings(selectedArch, valX, bv, arraysSize, clientsOnline, 0));
                break;
        }
    }

    private void run(BenchmarkSettings bs) {
        BenchmarkSettings.setConfig(bs);
        String filename = "empty";
        String host = "192.168.1.70";
        switch (bs.getBoundedValue().getType()) {
            case NUMBERS_IN_ARRAY:
                filename = "changing_N";
                for (int i = bs.getBoundedValue().getLowerBound(); (i <= bs.getBoundedValue().getUpperBound()) && (!error); i += bs.getBoundedValue().getStep()) {
                    Thread[] clients = new Thread[bs.getClientsOnline()];
                    for (int j = 0; j < bs.getClientsOnline(); j++) {
                        clients[j] = new Thread(new ClientRunner(new Client(new ClientBaggage(host, 8228, bs.getQueriesNumber(), i, bs.getDelta(), i))));
                    }

                    for (int j = 0; j < bs.getClientsOnline(); j++) {
                        clients[j].start();
                    }

                    for (int j = 0; j < bs.getClientsOnline(); j++) {
                        try {
                            clients[j].join();
                        } catch (InterruptedException e) {
                            error = true;
                            logger.info("Thread interrupted  " + e.getMessage());
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Info");
                            alert.setHeaderText(null);
                            alert.setContentText("error in thread where client was runned, probably with connection top server!");
                            alert.showAndWait();
                        }
                    }
                }
                break;
            case CLIENTS_ONLINE:
                filename = "changing_M";
                for (int i = bs.getBoundedValue().getLowerBound();(i <= bs.getBoundedValue().getUpperBound()) && (!error); i += bs.getBoundedValue().getStep()) {
                    Thread[] clients = new Thread[i];
                    for (int j = 0; j < i; j++) {
                        clients[j] = new Thread(new ClientRunner(new Client(new ClientBaggage(host, 8228, bs.getQueriesNumber(), bs.getArraysSize(), bs.getDelta(), i))));
                    }

                    for (int j = 0; j < i; j++) {
                        clients[j].start();
                    }

                    for (int j = 0; j < i; j++) {
                        try {
                            clients[j].join();
                        } catch (InterruptedException e) {
                            error = true;
                            logger.info("Thread interrupted  " + e.getMessage());
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Info");
                            alert.setHeaderText(null);
                            alert.setContentText("error in thread where client was runned, probably with connection top server!");
                            alert.showAndWait();
                        }
                    }
                }
                break;
            case QUERY_DELAY:
                filename = "changing_Delay";
                for (int i = bs.getBoundedValue().getLowerBound();(i <= bs.getBoundedValue().getUpperBound()) && (!error); i += bs.getBoundedValue().getStep()) {
                    Thread[] clients = new Thread[bs.getClientsOnline()];
                    for (int j = 0; j < bs.getClientsOnline(); j++) {
                        clients[j] = new Thread(new ClientRunner(new Client(new ClientBaggage(host, 8228, bs.getQueriesNumber(), bs.getArraysSize(), i, i))));
                    }

                    for (int j = 0; j < bs.getClientsOnline(); j++) {
                        clients[j].start();
                    }

                    for (int j = 0; j < bs.getClientsOnline(); j++) {
                        try {
                            clients[j].join();
                        } catch (InterruptedException e) {
                            error = true;
                            logger.info("Thread interrupted  " + e.getMessage());
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Info");
                            alert.setHeaderText(null);
                            alert.setContentText("error in thread where client was runned, probably with connection top server!");
                            alert.showAndWait();
                        }
                    }
                }
                break;
        }

        System.out.println("hooray");

        if (error) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText(null);
            alert.setContentText("Error occurred, testing failed");
            alert.showAndWait();
        }

        try {
            StatisticCollector.writeToFile(filename);
        } catch (FileNotFoundException e) {
        }

        chart = new LineChart<>(xAxis, yAxis);
        yAxis.setLabel("time");

        switch (BenchmarkSettings.getConfig().getBoundedValue().getType()) {
            case NUMBERS_IN_ARRAY:
                xAxis.setLabel("Numbers in array");
                break;
            case CLIENTS_ONLINE:
                xAxis.setLabel("Clients online");
                break;
            case QUERY_DELAY:
                xAxis.setLabel("Query delay");
                break;
        }


        writeLines();
        StageContainer.getStage().setScene(new Scene(chart, 600, 600));
        StageContainer.getStage().show();
    }

    @AllArgsConstructor
    private class ClientRunner implements Runnable {
        private Client client;

        @Override
        public void run() {
            try {
                client.runClient();
            } catch (IOException e) {
                logger.info("Cannot connect to server " + e.getMessage());
                error = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText(null);
                alert.setContentText("error in thread where client was runned, probably with connection top server!");
                alert.showAndWait();
            } catch (ClientError clientError) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                error = true;
                alert.setTitle("Info");
                alert.setHeaderText(null);
                alert.setContentText("Client havent finish testing");
                alert.showAndWait();
            }
        }
    }

    public void writeLines() {
        ArrayList<StatisticCollector.MyEntry> first = new ArrayList<>();
        ArrayList<StatisticCollector.MyEntry> second = new ArrayList<>();
        ArrayList<StatisticCollector.MyEntry> third = new ArrayList<>();
        StatisticCollector.getPlots().forEach((integer, graphPoint) -> {
            int delimiter = graphPoint.getClientsNumber();
            first.add(new StatisticCollector.MyEntry(integer, graphPoint.getSumSortingTimes() / delimiter));
            second.add(new StatisticCollector.MyEntry(integer, graphPoint.getSumRequestTimes() / delimiter));
            third.add(new StatisticCollector.MyEntry(integer, graphPoint.getSumAvgQueryTimes() / delimiter));
        });

        Collections.sort(first);
        Collections.sort(second);
        Collections.sort(third);

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Sort time");

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("ServerQuery time");

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Local client time");

        for (StatisticCollector.MyEntry entry : first) {
            series1.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
        }

        for (StatisticCollector.MyEntry entry : second) {
            series2.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
        }

        for (StatisticCollector.MyEntry myEntry : third) {
            series3.getData().add(new XYChart.Data(myEntry.getKey(), myEntry.getValue()));
        }


        chart.getData().add(series1);
        chart.getData().add(series2);
        chart.getData().add(series3);
    }

    private ArchitectureType getArchType() {
        int index = architecture.getSelectionModel().getSelectedIndex();
        return ArchitectureType.values()[index];
    }

    private BoundedValue getVariable() {
        int index = variableToChange.getSelectionModel().getSelectedIndex();
        int lower = parseValue(lowerBound, 1);
        int upper = parseValue(upperBound, 10);
        int step = parseValue(stepBound, 1);

        return new BoundedValue(VariableValues.values()[index], lower, upper, step);
    }

    private int parseValue(TextField data, int defaultValue) {
        int val;
        try {
            val = Integer.parseInt(data.getText());
        } catch (NumberFormatException ex) {
            logger.info("Value in input was incorrect");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect input, set to default!");
            alert.showAndWait();
            val = defaultValue;
        }

        return val;
    }
}
