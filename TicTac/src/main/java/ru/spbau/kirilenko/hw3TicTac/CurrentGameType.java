package ru.spbau.kirilenko.hw3TicTac;

/**
 * Class that stores current game type
 */
public class CurrentGameType {
    private static int currentType = 0;

    /**
     * Method that can change current game type
     * @param newType new game type
     */
    public static void setType(int newType) {
        currentType = newType;
    }

    /**
     * Method that returns current game type
     * @return current game type
     */
    public static int getCurrentType() {
        return currentType;
    }
}
