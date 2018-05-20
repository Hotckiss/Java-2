package ru.spbau.kirilenko;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

/**
 * An exception thrown in case of invocation failure.
 */
@SuppressWarnings("WeakerAccess")
public class InvocationException extends Exception {
    @Getter private Method method;

    public InvocationException(@NotNull Method method, @NotNull Throwable cause) {
        super(cause);
        this.method = method;
    }
}
