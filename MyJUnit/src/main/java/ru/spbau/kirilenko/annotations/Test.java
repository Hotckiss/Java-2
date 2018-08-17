package ru.spbau.kirilenko.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation for marking methods as tests
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
    String ACTIVE = "notIgnored";
    String NO_EXCEPTIONS = "noExceptions";

    /**
     * Reason to ignore test
     * @return ACTIVE if the test is not ignored. Reason to ignore otherwise.
     */
    String ignoreReason() default ACTIVE;

    /**
     * Parameter for the expected exception
     * @return the name of the expected exception or NO_EXCEPTIONS if no exceptions are
     * expected.
     */
    String expected() default NO_EXCEPTIONS;
}