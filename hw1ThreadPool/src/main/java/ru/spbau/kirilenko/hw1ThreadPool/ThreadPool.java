package ru.spbau.kirilenko.hw1ThreadPool;

import java.util.function.Supplier;

/**
 * Interface that represents simple thread pool structure
 */
public interface ThreadPool {
    /**
     * Method that returns task which will execute some supplier
     */
    <T> LightFuture <T> submit(Supplier<T> supplier);

    /**
     * Kills all threads that were created by this instance of thread pool
     */
    void shutdown();
}
