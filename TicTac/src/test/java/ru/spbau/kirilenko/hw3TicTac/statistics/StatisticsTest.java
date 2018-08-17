package ru.spbau.kirilenko.hw3TicTac.statistics;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class that tests all statistics methods
 */
public class StatisticsTest {

    /**
     * Test get wins in easy game
     */
    @Test
    public void testGetWinsEasy() {
        int prevValue = Statistics.getWinsEasy();
        Statistics.incrementWinsEasy();
        assertEquals(prevValue + 1, Statistics.getWinsEasy());
    }

    /**
     * Test get draws in easy game
     */
    @Test
    public void testGetDrawsEasy() {
        int prevValue = Statistics.getDrawsEasy();
        Statistics.incrementDrawsEasy();
        assertEquals(prevValue + 1, Statistics.getDrawsEasy());
    }

    /**
     * Test get loses in easy game
     */
    @Test
    public void testGetLosesEasy() {
        int prevValue = Statistics.getLosesEasy();
        Statistics.incrementLosesEasy();
        assertEquals(prevValue + 1, Statistics.getLosesEasy());
    }

    /**
     * Test get wins in hard game
     */
    @Test
    public void testGetWinsHard() {
        int prevValue = Statistics.getWinsHard();
        Statistics.incrementWinsHard();
        assertEquals(prevValue + 1, Statistics.getWinsHard());
    }

    /**
     * Test get draws in hard game
     */
    @Test
    public void testGetDrawsHard() {
        int prevValue = Statistics.getDrawsHard();
        Statistics.incrementDrawsHard();
        assertEquals(prevValue + 1, Statistics.getDrawsHard());
    }

    /**
     * Test get loses in hard game
     */
    @Test
    public void testGetLosesHard() {
        int prevValue = Statistics.getLosesHard();
        Statistics.incrementLosesHard();
        assertEquals(prevValue + 1, Statistics.getLosesHard());
    }

    /**
     * Test get wins of X in hotseat game
     */
    @Test
    public void testGetWinsX() {
        int prevValue = Statistics.getWinsX();
        Statistics.incrementWinsX();
        assertEquals(prevValue + 1, Statistics.getWinsX());
    }

    /**
     * Test get wins of O in hotseat game
     */
    @Test
    public void testGetWinsO() {
        int prevValue = Statistics.getWinsO();
        Statistics.incrementWinsO();
        assertEquals(prevValue + 1, Statistics.getWinsO());
    }

    /**
     * Test get draws in hotseat game
     */
    @Test
    public void testGetDraws() {
        int prevValue = Statistics.getDraws();
        Statistics.incrementDraws();
        assertEquals(prevValue + 1, Statistics.getDraws());
    }

    /**
     * Test increment wins in easy game
     */
    @Test
    public void testIncrementWinsEasy() {
        int prevValue = Statistics.getWinsEasy();
        Statistics.incrementWinsEasy();
        assertEquals(prevValue + 1, Statistics.getWinsEasy());
    }

    /**
     * Test increment draws in easy game
     */
    @Test
    public void testIncrementDrawsEasy() {
        int prevValue = Statistics.getDrawsEasy();
        Statistics.incrementDrawsEasy();
        assertEquals(prevValue + 1, Statistics.getDrawsEasy());
    }

    /**
     * Test increment loses in easy game
     */
    @Test
    public void testIncrementLosesEasy() {
        int prevValue = Statistics.getLosesEasy();
        Statistics.incrementLosesEasy();
        assertEquals(prevValue + 1, Statistics.getLosesEasy());
    }

    /**
     * Test increment wins in hard game
     */
    @Test
    public void testIncrementWinsHard() {
        int prevValue = Statistics.getWinsHard();
        Statistics.incrementWinsHard();
        assertEquals(prevValue + 1, Statistics.getWinsHard());
    }

    /**
     * Test increment draws in hard game
     */
    @Test
    public void testIncrementDrawsHard() {
        int prevValue = Statistics.getDrawsHard();
        Statistics.incrementDrawsHard();
        assertEquals(prevValue + 1, Statistics.getDrawsHard());
    }

    /**
     * Test increment loses in hard game
     */
    @Test
    public void testIncrementLosesHard() {
        int prevValue = Statistics.getLosesHard();
        Statistics.incrementLosesHard();
        assertEquals(prevValue + 1, Statistics.getLosesHard());
    }

    /**
     * Test increment wins of X in hotseat game
     */
    @Test
    public void testIncrementWinsX() {
        int prevValue = Statistics.getWinsX();
        Statistics.incrementWinsX();
        assertEquals(prevValue + 1, Statistics.getWinsX());
    }

    /**
     * Test increment draws in hotseat game
     */
    @Test
    public void testIncrementDraws() {
        int prevValue = Statistics.getDraws();
        Statistics.incrementDraws();
        assertEquals(prevValue + 1, Statistics.getDraws());
    }

    /**
     * Test increment wins of O in hotseat game
     */
    @Test
    public void testIncrementWinsO() {
        int prevValue = Statistics.getWinsO();
        Statistics.incrementWinsO();
        assertEquals(prevValue + 1, Statistics.getWinsO());
    }
}