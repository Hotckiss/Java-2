package ru.spbau.kirilenko;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for methods that are possible to test
 */
public class ControllerTest {
    @Test(expected = IllegalArgumentException.class)
    public void testControllerConstructorIncorrectArgument1() {
        new Controller(-10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testControllerConstructorIncorrectArgument2() {
        new Controller(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testControllerConstructorIncorrectArgument3() {
        new Controller(11);
    }

    @Test
    public void testGeneratingFieldSize1() {
        assertEquals(2, FindPair.generateFieldSize(new String[]{"1"}));
    }

    @Test
    public void testGeneratingFieldSize2() {
        assertEquals(16, FindPair.generateFieldSize(new String[]{"100"}));
    }

    @Test
    public void testGeneratingFieldSize3() {
        assertEquals(6, FindPair.generateFieldSize(new String[]{"-5"}));
    }

    @Test
    public void testGeneratingFieldSize4() {
        assertEquals(2, FindPair.generateFieldSize(new String[]{"0"}));
    }

    @Test
    public void testGeneratingFieldSize5() {
        assertEquals(8, FindPair.generateFieldSize(new String[]{"7"}));
    }

    @Test
    public void testGeneratingFieldSize6() {
        assertEquals(4, FindPair.generateFieldSize(new String[]{}));
    }
}