package ru.spbau.kirilenko;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class BenchmarkSettings {
    @Getter @Setter private static BenchmarkSettings config;

    @Getter private  ArchitectureType type;
    @Getter private int queriesNumber;
    @Getter private BoundedValue boundedValue;
    @Getter private int arraysSize;
    @Getter private int clientsOnline;
    @Getter private int delta;
}