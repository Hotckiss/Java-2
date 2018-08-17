package ru.spbau.kirilenko.hw4SimpleFTP;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.ConnectException;

import static ru.spbau.kirilenko.hw4SimpleFTP.ClientManager.CLIENT;
import static ru.spbau.kirilenko.hw4SimpleFTP.StageStorage.INSTANCE;

/**
 * Controller of the main menu, allows to connect to server using host and port
 */
public class MenuController {
    @FXML public TextField hostInput;

    @FXML public TextField portInput;

    @FXML public Button connectButton;

    /**
     * Trying to connect to ftp server with given host and port
     * @throws IOException if server is not available
     */
    public void connect() throws IOException {
        String host = hostInput.getText();
        String port = portInput.getText();
        Integer portValue;

        try {
            portValue = Integer.parseInt(port);
        } catch (Exception ex) {
            showError("No such port.");
            return;
        }

        if (portValue < 0) {
            showError("No such port.");
            return;
        }

        try {
            CLIENT.setClient(new Client(host, portValue));
            INSTANCE.getServerScene();
        } catch (ConnectException ce) {
            showError("Cannot connect to server!");
        }
    }

    private void showError(@NotNull String message) {
        new Alert(Alert.AlertType.ERROR, message).showAndWait();
    }
}
