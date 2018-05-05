package ru.spbau.kirilenko.hw3TicTac;

/**
 * Class that stores current game type
 */
public class CurrentGameType {
    private static GameTypes currentType = GameTypes.EASY_GAME;

    /**
     * Method that can change current game type
     * @param newType new game type
     */
    public static void setType(GameTypes newType) {
        currentType = newType;
    }

    /**
     * Method that returns current game type
     * @return current game type
     */
    public static GameTypes getCurrentType() {
        return currentType;
    }
}
