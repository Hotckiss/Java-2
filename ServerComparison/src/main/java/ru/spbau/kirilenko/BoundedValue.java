package ru.spbau.kirilenko;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BoundedValue {
    @Getter private VariableValues type;
    @Getter private int lowerBound;
    @Getter private int upperBound;
    @Getter private int step;
}
