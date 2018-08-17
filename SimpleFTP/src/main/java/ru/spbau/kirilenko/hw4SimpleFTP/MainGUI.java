package ru.spbau.kirilenko.hw4SimpleFTP;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import static ru.spbau.kirilenko.hw4SimpleFTP.StageStorage.INSTANCE;

/**
 * Main application class that launches client of the ftp server
 */
public class MainGUI extends Application {
    /**
     * Method that runs application
     * @param args user arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * Methods that set settings to the primary stage of the app
     * @param primaryStage app primary stage
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Simple FTP");
        primaryStage.setResizable(false);

        INSTANCE.setStage(primaryStage);
        INSTANCE.getMainMenuScene();
        primaryStage.show();
    }
}
