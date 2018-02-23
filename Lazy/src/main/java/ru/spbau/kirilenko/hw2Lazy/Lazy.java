package ru.spbau.kirilenko.hw2Lazy;

import org.jetbrains.annotations.Nullable;

public interface Lazy<T> {
    @Nullable T get();
}
