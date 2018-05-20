package ru.spbau.kirilenko;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 * Class with test results
 */
@SuppressWarnings("WeakerAccess")
public class TestScore {
    @Getter private final String name;
    @Getter @Setter private TestResult result;
    @Getter @Setter private long runningTime;
    @Getter @Setter private String ignoreReason;
    @Getter @Setter private Throwable failureReason;

    public TestScore(@NotNull String name) {
        this.name = name;
    }
}