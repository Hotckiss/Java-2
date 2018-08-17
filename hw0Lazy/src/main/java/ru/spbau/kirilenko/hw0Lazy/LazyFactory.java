package ru.spbau.kirilenko.hw0Lazy;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Provides to instantiate lazy computations which can be run in one thread or
 * thread-safe instance of lazy computation
 */
@SuppressWarnings("WeakerAccess")
public class LazyFactory {
    /**
     * Method that creates lazy computing that will lazy compute the result
     * of supplier function
     * @param supplier supplier with computation
     * @param <T> return value type
     * @return lazy computation
     */
    public static <T> Lazy<T> createSingleThreadLazy(@NotNull final Supplier<T> supplier) {
        return new SingleThreadLazy<>(supplier);
    }

    /**
     * Method that creates lazy computing that will lazy compute the result
     * of supplier function, and it will be computed only once even if many threads
     * will call the computation
     * @param supplier supplier with computation
     * @param <T> return value type
     * @return lazy computation
     */
    public static <T> Lazy<T> createMultiThreadLazy(@NotNull final Supplier<T> supplier) {
        return new MultiThreadLazy<>(supplier);
    }

    /**
     * Implementation of the single thread lazy computation
     * @param <T> return value type
     */
    private static final class SingleThreadLazy<T> implements Lazy<T> {
        private static final Object EMPTY = new Object();

        private Supplier<T> supplier;
        private Object value = EMPTY;

        /**
         * Creates lazy with input computation
         * @param supplier computation
         */
        public SingleThreadLazy(@NotNull final Supplier<T> supplier) {
            this.supplier = supplier;
        }

        /**
         * Method that computes result at first call
         * and then only returns computation result
         * @return computation result
         */
        @Override
        @SuppressWarnings("unchecked")
        public @Nullable T get() {
            if (value == EMPTY) {
                value = supplier.get();
                supplier = null;
            }

            return (T) value;
        }
    }

    /**
     * Implementation of the multi thread lazy computation
     * @param <T> return value type
     */
    private static final class MultiThreadLazy<T> implements Lazy<T> {
        private static final Object EMPTY = new Object();

        private volatile Supplier<T> supplier;
        private volatile Object value = EMPTY;

        /**
         * Creates lazy with input computation
         * @param supplier computation
         */
        public MultiThreadLazy(@NotNull final Supplier<T> supplier) {
            this.supplier = supplier;
        }

        /**
         * Method that computes result at first call
         * and then only returns computation result
         * @return computation result
         */
        @Override
        @SuppressWarnings("unchecked")
        public @Nullable T get() {
            if (value != EMPTY) {
                return (T) value;
            }

            synchronized (this) {
                if (value == EMPTY) {
                    value = supplier.get();
                    supplier = null;
                }
            }

            return (T) value;
        }
    }
}

