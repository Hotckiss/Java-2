package ru.spbau.kirilenko.hw3TicTac.statistics;

/**
 * Class that stores all games results
 * collected by game type
 */
public class Statistics {
    private static int winsEasy = 0;
    private static int drawsEasy = 0;
    private static int losesEasy = 0;
    private static int winsHard = 0;
    private static int drawsHard = 0;
    private static int losesHard = 0;
    private static int winsX = 0;
    private static int winsO = 0;
    private static int draws = 0;

    /**
     * Method that returns number of wins against easy AI
     * @return number of wins against easy AI
     */
    public static int getWinsEasy() {
        return winsEasy;
    }

    /**
     * Method that returns number of draws against easy AI
     * @return number of draws against easy AI
     */
    public static int getDrawsEasy() {
        return drawsEasy;
    }

    /**
     * Method that returns number of loses against easy AI
     * @return number of loses against easy AI
     */
    public static int getLosesEasy() {
        return losesEasy;
    }

    /**
     * Method that returns number of wins against hard AI
     * @return number of wins against hard AI
     */
    public static int getWinsHard() {
        return winsHard;
    }

    /**
     * Method that returns number of draws against hard AI
     * @return number of draws against hard AI
     */
    public static int getDrawsHard() {
        return drawsHard;
    }

    /**
     * Method that returns number of loses against hard AI
     * @return number of loses against hard AI
     */
    public static int getLosesHard() {
        return losesHard;
    }

    /**
     * Method that returns number of wins for X in hotseat game
     * @return number of wins for X in hotseat game
     */
    public static int getWinsX() {
        return winsX;
    }

    /**
     * Method that returns number of wins for O in hotseat game
     * @return number of wins for O in hotseat game
     */
    public static int getWinsO() {
        return winsO;
    }

    /**
     * Method that returns number of draws in hotseat game
     * @return number of draws in hotseat game
     */
    public static int getDraws() {
        return draws;
    }

    /**
     * Method that adds win against easy AI
     */
    public static void incrementWinsEasy() {
        winsEasy++;
    }

    /**
     * Method that adds draw against easy AI
     */
    public static void incrementDrawsEasy() {
        drawsEasy++;
    }

    /**
     * Method that adds lose against easy AI
     */
    public static void incrementLosesEasy() {
        losesEasy++;
    }

    /**
     * Method that adds win against hard AI
     */
    public static void incrementWinsHard() {
        winsHard++;
    }

    /**
     * Method that adds draw against hard AI
     */
    public static void incrementDrawsHard() {
        drawsHard++;
    }

    /**
     * Method that adds lose against hard AI
     */
    public static void incrementLosesHard() {
        losesHard++;
    }

    /**
     * Method that adds win for X in hotseat game
     */
    public static void incrementWinsX() {
        winsX++;
    }

    /**
     * Method that adds win for O in hotseat game
     */
    public static void incrementDraws() {
        draws++;
    }

    /**
     * Method that adds draw in hotseat game
     */
    public static void incrementWinsO() {
        winsO++;
    }
}
