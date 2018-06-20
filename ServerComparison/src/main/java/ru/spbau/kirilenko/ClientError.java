package ru.spbau.kirilenko;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ClientError extends Throwable {
    @Getter private String message;
}
