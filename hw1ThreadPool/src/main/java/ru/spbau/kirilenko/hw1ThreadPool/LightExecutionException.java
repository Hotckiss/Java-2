package ru.spbau.kirilenko.hw1ThreadPool;

/**
 * Exception that will be thrown if there was an exception
 * during task execution
 */
@SuppressWarnings("WeakerAccess")
public class LightExecutionException extends RuntimeException {
    LightExecutionException(Exception e) {
        super(e);
    }
}