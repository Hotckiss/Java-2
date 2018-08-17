package ru.spbau.kirilenko.hw3TicTac;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main method of the app that loads main window and set main app settings
 */
public class MainActivity extends Application {

    /**
     * Method that launches application
     * @param args app arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * Methods that set settings to the primary stage of the app
     * @param primaryStage app primary stage
     * @throws IOException if error during loading scene occurred
     */
    @Override
    public void start(Stage primaryStage) throws IOException{
        ScenesLoader.setStage(primaryStage);

        primaryStage.getIcons().add(new Image("/icon.png"));
        primaryStage.setTitle("Tic-Tac Toe");
        primaryStage.setResizable(false);
        primaryStage.setScene(ScenesLoader.getMainMenuScene());
        primaryStage.show();
    }
}
