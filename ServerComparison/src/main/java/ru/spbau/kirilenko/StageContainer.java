package ru.spbau.kirilenko;

import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class StageContainer {
    @Getter @Setter private static Stage stage;
}
