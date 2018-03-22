package ru.spbau.kirilenko.hw3TicTac.logic;

/**
 * Class that stores two elements of any type
 * @param <T> first value type
 * @param <U> second value type
 */
@SuppressWarnings("WeakerAccess")
public class MyPair<T, U> {
    private T first;
    private U second;

    /**
     * Constructs pait instance using two elements
     * @param first first element to store
     * @param second second element to store
     */
    public MyPair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Method that returns first stored value
     * @return first stored value
     */
    public T getFirst() {
        return first;
    }

    /**
     * Method that returns second stored value
     * @return second stored value
     */
    public U getSecond() {
        return second;
    }

    /**
     * Method that changes first stored value
     * @param first new first value
     */
    public void setFirst(T first) {
        this.first = first;
    }

    /**
     * Method that changes second stored value
     * @param second new second value
     */
    public void setSecond(U second) {
        this.second = second;
    }
}
