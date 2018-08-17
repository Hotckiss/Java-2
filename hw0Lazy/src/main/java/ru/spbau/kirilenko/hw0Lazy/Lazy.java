package ru.spbau.kirilenko.hw0Lazy;

import org.jetbrains.annotations.Nullable;

/**
 * Interface that reperesents lazy computation
 * @param <T> return value of the computation
 */
public interface Lazy<T> {
    /**
     * Method that computes result
     * It must guarantee that computation will be only once
     * and then it will return value immediately after calling
     * @return computation result
     */
    @Nullable T get();
}