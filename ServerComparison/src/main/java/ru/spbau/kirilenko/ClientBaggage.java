package ru.spbau.kirilenko;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ClientBaggage {
    @Getter private final String host;
    @Getter private final int port;
    @Getter private final int queriesNumber;
    @Getter private final int arraySize;
    @Getter private final int delay;
    @Getter private final int key;
}
