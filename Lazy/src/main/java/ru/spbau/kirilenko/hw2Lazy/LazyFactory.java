package ru.spbau.kirilenko.hw2Lazy;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class LazyFactory {
    public static <T> Lazy<T> createSingleThreadLazy(@NotNull final Supplier<T> supplier) {
        return new SingleThreadLazy<>(supplier);
    }

    public static <T> Lazy<T> createMultiThreadLazy(@NotNull final Supplier<T> supplier) {
        return new MultiThreadLazy<>(supplier);
    }

    private static final class SingleThreadLazy<T> implements Lazy<T> {
        private static final Object EMPTY = new Object();

        private Supplier<T> supplier;
        private Object value = EMPTY;

        public SingleThreadLazy(@NotNull final Supplier<T> supplier) {
            this.supplier = supplier;
        }

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

    private static final class MultiThreadLazy<T> implements Lazy<T> {
        private static final Object EMPTY = new Object();

        private volatile Supplier<T> supplier;
        private volatile Object value = EMPTY;

        public MultiThreadLazy(@NotNull final Supplier<T> supplier) {
            this.supplier = supplier;
        }

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
