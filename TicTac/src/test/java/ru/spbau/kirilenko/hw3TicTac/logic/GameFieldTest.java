package ru.spbau.kirilenko.hw3TicTac.logic;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static ru.spbau.kirilenko.hw3TicTac.logic.FieldMarks.*;

/**
 * Class that tests all methods of game filed class
 */
public class GameFieldTest {
    private GameField gf;

    /**
     * Init test game field
     */
    @Before
    public void init() {
        gf = new GameField();
    }

    /**
     * Testing put mark on the field
     */
    @Test
    public void testPutMark() {
        gf.putMark(1, 1, O);

        assertEquals(O, gf.getMark(1, 1));
    }

    /**
     * Testing get mark from the field
     */
    @Test
    public void testGetMark() {
        gf.putMark(1, 1, X);

        assertEquals(X, gf.getMark(1, 1));
    }

    /**
     * Testing accessibility of the field element
     */
    @Test
    public void testIsAccessible() {
        assertTrue(gf.isAccessible(1, 1));
        gf.putMark(1, 1, X);
        assertFalse(gf.isAccessible(1, 1));
    }

    /**
     * Testing get empty field method
     */
    @Test
    public void testGetEmptyFields() {
        gf.putMark(0, 0, X);
        gf.putMark(0, 1, X);
        gf.putMark(0, 2, X);
        gf.putMark(1, 0, X);
        gf.putMark(1, 1, X);
        gf.putMark(1, 2, X);

        ArrayList<Pair<Integer, Integer>> actual = gf.getEmptyFields();
        ArrayList<Pair<Integer, Integer>> expected = new ArrayList<>();
        expected.add(new Pair<>(2, 0));
        expected.add(new Pair<>(2, 1));
        expected.add(new Pair<>(2, 2));

        for(int i = 0; i < 3; i++) {
            assertThat(expected.get(i).getKey(), is(actual.get(i).getKey()));
            assertThat(expected.get(i).getValue(), is(actual.get(i).getValue()));
        }
    }

    /**
     * Testing field is full method
     */
    @Test
    public void testIsFull() {
        assertFalse(gf.isFull());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gf.putMark(i, j, X);
            }
        }
        assertTrue(gf.isFull());
    }

    /**
     * Testing converting field to string method
     */
    @Test
    public void testToString() {
        gf.putMark(0, 0, X);
        gf.putMark(1, 1, X);
        gf.putMark(2, 2, X);

        assertThat(gf.toString(), is("100010001"));
    }
}