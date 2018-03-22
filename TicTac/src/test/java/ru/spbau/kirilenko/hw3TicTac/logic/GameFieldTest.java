package ru.spbau.kirilenko.hw3TicTac.logic;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

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
        gf.putMark(1, 1, 4);

        assertEquals(4, gf.getMark(1, 1));
    }

    /**
     * Testing get mark from the field
     */
    @Test
    public void testGetMark() {
        gf.putMark(1, 1, 1);

        assertEquals(1, gf.getMark(1, 1));
    }

    /**
     * Testing accessibility of the field element
     */
    @Test
    public void testIsAccessible() {
        assertTrue(gf.isAccessible(1, 1));
        gf.putMark(1, 1, 1);
        assertFalse(gf.isAccessible(1, 1));
    }

    /**
     * Testing get empty field method
     */
    @Test
    public void testGetEmptyFields() {
        gf.putMark(0, 0, 1);
        gf.putMark(0, 1, 1);
        gf.putMark(0, 2, 1);
        gf.putMark(1, 0, 1);
        gf.putMark(1, 1, 1);
        gf.putMark(1, 2, 1);

        ArrayList<MyPair<Integer, Integer>> actual = gf.getEmptyFields();
        ArrayList<MyPair<Integer, Integer>> expected = new ArrayList<>();
        expected.add(new MyPair<>(2, 0));
        expected.add(new MyPair<>(2, 1));
        expected.add(new MyPair<>(2, 2));

        for(int i = 0; i < 3; i++) {
            assertThat(expected.get(i).getFirst(), is(actual.get(i).getFirst()));
            assertThat(expected.get(i).getSecond(), is(actual.get(i).getSecond()));
        }
    }

    /**
     * Testing row product of the field
     */
    @Test
    public void testGetRowProduct() {
        gf.putMark(1, 0, 3);
        gf.putMark(1, 1, 5);
        gf.putMark(1, 2, 1);
        assertEquals(15, gf.getRowProduct(1));
    }

    /**
     * Testing col product of the field
     */
    @Test
    public void testGetColProduct() {
        gf.putMark(0, 1, 3);
        gf.putMark(1, 1, 5);
        gf.putMark(2, 1, 1);
        assertEquals(15, gf.getColProduct(1));
    }

    /**
     * Testing main diagonal product of the field
     */
    @Test
    public void testGetMainDiagProduct() {
        gf.putMark(0, 0, 3);
        gf.putMark(1, 1, 5);
        gf.putMark(2, 2, 1);
        assertEquals(15, gf.getMainDiagProduct());
    }

    /**
     * Testing other diagonal product of the field
     */
    @Test
    public void testGetOtherDiagProduct() {
        gf.putMark(0, 2, 3);
        gf.putMark(1, 1, 5);
        gf.putMark(2, 0, 1);
        assertEquals(15, gf.getOtherDiagProduct());
    }

    /**
     * Testing field is full method
     */
    @Test
    public void testIsFull() {
        assertFalse(gf.isFull());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gf.putMark(i, j, 1);
            }
        }
        assertTrue(gf.isFull());
    }

    /**
     * Testing row sum of the field
     */
    @Test
    public void testGetRowSum() {
        gf.putMark(1, 0, 3);
        gf.putMark(1, 1, 5);
        gf.putMark(1, 2, 1);
        assertEquals(9, gf.getRowSum(1));
    }

    /**
     * Testing column sum of the field
     */
    @Test
    public void testGetColSum() {
        gf.putMark(0, 1, 3);
        gf.putMark(1, 1, 5);
        gf.putMark(2, 1, 1);
        assertEquals(9, gf.getColSum(1));
    }

    /**
     * Testing main diagonal sum of the field
     */
    @Test
    public void testGetMainDiagSum() {
        gf.putMark(0, 0, 3);
        gf.putMark(1, 1, 5);
        gf.putMark(2, 2, 1);
        assertEquals(9, gf.getMainDiagSum());
    }

    /**
     * Testing other diagonal sum of the field
     */
    @Test
    public void testGetOtherDiagSum() {
        gf.putMark(0, 2, 3);
        gf.putMark(1, 1, 5);
        gf.putMark(2, 0, 1);
        assertEquals(9, gf.getOtherDiagSum());
    }

    /**
     * Testing converting field to string method
     */
    @Test
    public void testToString() {
        gf.putMark(0, 0, 1);
        gf.putMark(1, 1, 1);
        gf.putMark(2, 2, 1);

        assertThat(gf.toString(), is("100010001"));
    }
}