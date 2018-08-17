package ru.spbau.kirilenko.hw1ThreadPool;

import java.util.function.Function;

/**
 * Interface that represents task that will be completed in the future
 */
public interface LightFuture<T> {
    /**
     * Method that check if task had been completed
     */
    boolean isReady();

    /**
     * Method that returns result of the task or wait until it
     * will be completed.
     * If exception was thrown during execution, LightExecutionException
     * will be thrown
     */
    T get() throws LightExecutionException;

    /**
     * Method that returns new task which applies function to the result of the current task
     */
    <U> LightFuture<U> thenApply(Function<? super T, ? extends U> function);
}
