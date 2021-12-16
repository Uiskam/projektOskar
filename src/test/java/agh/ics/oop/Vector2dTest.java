package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class Vector2dTest {
    @Test
    public void equalsTest() {
        assertFalse(new Vector2d(1,2).equals(new Vector2d(2,3)));
        assertTrue(new Vector2d(12,31).equals(new Vector2d(12,31)));
        assertFalse(new Vector2d(2,3).equals(123));
    }

    @Test
    public void toStringTest() {
        assertEquals(new Vector2d(3,4).toString(),"(3,4)");
        assertNotEquals(new Vector2d(4,5).toString(),"( 4,5)");
        assertNotEquals(new Vector2d(4,5).toString(),"(4, 5)");
    }

    @Test
    public void precedesTest() {
        assertTrue(new Vector2d(1,2).precedes(new Vector2d(1,2)));
        assertTrue(new Vector2d(1,2).precedes(new Vector2d(5,4)));
        assertFalse(new Vector2d(1,2).precedes(new Vector2d(3,0)));

    }

    @Test
    public void followsTest() {
        assertTrue(new Vector2d(3,4).follows(new Vector2d(3,4)));
        assertFalse(new Vector2d(3,4).follows(new Vector2d(4,1)));
        assertTrue(new Vector2d(2,6).follows(new Vector2d(-1,0)));
    }

    @Test
    public void upperRightTest() {
        assertEquals(new Vector2d(1,6).upperRight(new Vector2d(3,4)),new Vector2d(3,6));
    }

    @Test
    public void lowerLeftTest() {
        assertEquals(new Vector2d(1,6).lowerLeft(new Vector2d(3,4)),new Vector2d(1,4));
    }

    @Test
    public void addTest() {
        assertEquals(new Vector2d(1,2).add(new Vector2d(6,3)),new Vector2d(7,5));
        assertEquals(new Vector2d(1,2).add(new Vector2d(6,3)),new Vector2d(3,6).add(new Vector2d(4,-1)));
    }

    @Test
    public void substractTest() {
        assertEquals(new Vector2d(1,2).subtract(new Vector2d(6,3)),new Vector2d(-5,-1));
        assertEquals(new Vector2d(1,2).subtract(new Vector2d(6,3)),new Vector2d(-1,0).subtract(new Vector2d(4,1)));
    }
}
