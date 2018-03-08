package ru.spbau.kirilenko.hw1ThreadPool;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Implementation of the ThreadPool interface with
 * fixed number of threads
 */
@SuppressWarnings("WeakerAccess")
public class ThreadPoolImpl implements ThreadPool {
    private final Queue<LightFutureAbstract> tasks = new ArrayDeque<>();
    private final List<Thread> threads = new ArrayList<>();

    /**
     * Creates ThreadPool with some number of threads, which can execute tasks
     */
    public ThreadPoolImpl(int numberOfThreads) {
        if (numberOfThreads <= 0) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < numberOfThreads; i++) {
            Thread newWorker = new Thread(new Worker());
            threads.add(newWorker);
            newWorker.start();
        }
    }

    /**
     * Method that submits new task into thread pool
     * @param supplier task to execute
     * @param <T> return value type
     * @return light future class that allows user to control task execution
     */
    @Override
    public <T> LightFuture<T> submit(Supplier<T> supplier) {
        LightFutureImpl<T> future = new LightFutureImpl<>(supplier);
        synchronized (tasks) {
            tasks.add(future);
            tasks.notifyAll();
        }

        return future;
    }

    /**
     * Class that represents thread pool worker
     * It takes tasks and executes them, if such tasks exists
     * If not, it waits until task will be added
     */
    private class Worker implements Runnable {
        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    final LightFutureAbstract future;
                    synchronized (tasks) {
                        while (tasks.isEmpty()) {
                            tasks.wait();
                        }
                        future = tasks.remove();
                    }
                    synchronized (future) {
                        future.execute();
                        future.notifyAll();
                    }
                    synchronized (tasks) {
                        tasks.notify();
                    }
                }
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    /**
     * Class that represents common methods implementation for
     * simple tasks and task with calling function after getting result
     * @param <T> return value type
     */
    private abstract class LightFutureAbstract<T> implements LightFuture<T> {
        protected volatile boolean isReady;
        protected volatile T result;
        protected volatile Exception executionException;

        /**
         * {@inheritDoc}
         */
        @Override
        public synchronized boolean isReady() {
            return this.isReady;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public synchronized T get() throws LightExecutionException {
            try {
                while (!isReady()) {
                    wait();
                }
                if (executionException != null) {
                    throw executionException;
                }
            } catch (Exception ex) {
                throw new LightExecutionException(ex);
            }

            return result;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public synchronized <U> LightFuture<U> thenApply(@NotNull Function<? super T, ? extends U> function) {
            LightFutureAbstract<U> taskApply = new LightFutureThenApply<>(function, this);
            synchronized (tasks) {
                tasks.add(taskApply);
                tasks.notifyAll();
            }
            return taskApply;
        }

        /**
         * Method that should be called by thread pool worker to
         * start executing task
         */
        public abstract void execute();
    }

    /**
     * Class that represents execute method for simple task
     * @param <T> return value type
     */
    private class LightFutureImpl<T> extends LightFutureAbstract<T> {
        private final Supplier<T> supplier;

        public LightFutureImpl(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void execute() {
            try {
                result = supplier.get();
            } catch (Exception e) {
                executionException = e;
            } finally {
                isReady = true;
            }
        }
    }

    /**
     * Class that represents execute method for task
     * which must apply function to result, after getting it
     * @param <T> return value type
     */
    private class LightFutureThenApply<T, U> extends LightFutureAbstract<U> {
        private final Function<? super T, ? extends U> function;
        private final LightFuture<T> parent;

        public LightFutureThenApply(@NotNull Function<? super T, ? extends U> function,
                             @NotNull LightFuture<T> parent) {
            this.function = function;
            this.parent = parent;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void execute() {
            try {
                result = function.apply(parent.get());
            } catch (LightExecutionException e) {
                executionException= (Exception) e.getCause();
            } finally {
                isReady = true;
            }
        }
    }
}
