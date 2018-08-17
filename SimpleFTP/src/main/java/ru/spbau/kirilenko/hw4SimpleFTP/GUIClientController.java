package ru.spbau.kirilenko.hw4SimpleFTP;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.spbau.kirilenko.hw4SimpleFTP.ClientManager.CLIENT;
import static ru.spbau.kirilenko.hw4SimpleFTP.StageStorage.INSTANCE;

/**
 * Class that updates listed files and can download them
 */
public class GUIClientController {
    private static final String DIRECTORY_STYLE = "-fx-background-color: #00ffaa; -fx-border-radius: 0; -fx-background-radius: 0; -fx-border-color: black;";
    private static final String FILE_STYLE = "-fx-background-color: #33aabb; -fx-border-radius: 0; -fx-background-radius: 0; -fx-border-color: black;";
    private static final int BUFFER_SIZE = 1024;

    @FXML public Button backButton;
    @FXML public Button disconnectButton;
    @FXML public VBox files;

    private ArrayList<String> currentPath = new ArrayList<>();
    private int depth = 0;

    /**
     * Init path to the server root folder and list files in it
     */
    @FXML public void initialize() {
        currentPath.add(".");
        try {
            uploadList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns back tho the previous level, or disconnects from server if folder is root
     * @throws IOException if connection was lost
     */
    public void back() throws IOException {
        if (depth == 0) {
            CLIENT.getClient().disconnect();
            INSTANCE.getMainMenuScene();
        } else {
            depth--;
            currentPath.remove(currentPath.size() - 1);
            uploadList();
        }
    }

    /**
     * Disconnects from server and return to main menu
     * @throws IOException if connection was lost
     */
    public void disconnect() throws IOException {
        depth = 0;
        CLIENT.getClient().disconnect();
        INSTANCE.getMainMenuScene();
    }

    private void uploadList() throws IOException {
        List<FileInfo> queryResult = CLIENT.getClient().list(String.join(File.separator, currentPath));
        Collections.sort(queryResult);

        Button[] content = new Button[queryResult.size()];

        for (int i = 0; i < queryResult.size(); i++) {
            FileInfo currentObject = queryResult.get(i);
            content[i] = new Button(currentObject.getName());
            content[i].setPrefWidth(580);
            content[i].setStyle(currentObject.isDirectory() ? DIRECTORY_STYLE : FILE_STYLE);
            content[i].setOnMouseClicked(new ButtonHandler(currentObject));
        }

        files.getChildren().setAll(content);
    }

    private void writeFile(@NotNull FileOutputStream fileOutputStream, @NotNull byte[] fileContent) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileContent);

        byte[] buffer = new byte[BUFFER_SIZE];
        long read = 0;

        while (read < fileContent.length) {
            int count = (int) Math.min(BUFFER_SIZE, fileContent.length - read);
            int readActual = byteArrayInputStream.read(buffer, 0, count);
            fileOutputStream.write(buffer, 0, readActual);
            read += readActual;
        }

        byteArrayInputStream.close();

    }

    private void addAndReload(@NotNull String folderName) {
        currentPath.add(folderName);
        depth++;

        try {
            uploadList();
        } catch (IOException e) {
            showError("Error occurred while loading folder content, try again!");
        }
    }

    private class ButtonHandler implements EventHandler<MouseEvent> {
        @NotNull private final FileInfo fileInfo;

        @SuppressWarnings("WeakerAccess")
        public ButtonHandler(@NotNull FileInfo fileInfo) {
            this.fileInfo = fileInfo;
        }

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (fileInfo.isDirectory()) {
                    addAndReload(fileInfo.getName());
                    return;
                }

                String confirmation = "Download " + fileInfo.getName() + "?";
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, confirmation, ButtonType.YES, ButtonType.CANCEL);
                alert.showAndWait();

                if (alert.getResult() != ButtonType.YES) {
                    return;
                }

                FileChooser fileChooser = new FileChooser();
                File dest = fileChooser.showSaveDialog(INSTANCE.getStage());

                if (dest == null) {
                    alert.close();
                    return;
                }

                try (FileOutputStream fileOutputStream = new FileOutputStream(dest)) {
                    byte[] fileContent = CLIENT.getClient().get(String.join(File.separator, currentPath) +
                            File.separator + fileInfo.getName());
                    //noinspection ResultOfMethodCallIgnored
                    dest.createNewFile();
                    writeFile(fileOutputStream, fileContent);
                } catch (IOException ex) {
                    showError("Error creating file! Check write permissions.");
                }
            }
        }
    }

    private void showError(@NotNull String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(error);
        alert.showAndWait();
    }
}
