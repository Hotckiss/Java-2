package ru.spbau.kirilenko;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Benchmark extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Servers benchmark");
        primaryStage.setResizable(false);
        StageContainer.setStage(primaryStage);
        primaryStage.setScene(new Scene(FXMLLoader.load(Benchmark.class.getResource("/MainMenu.fxml")), 500, 600));
        primaryStage.show();
    }
}
