package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDirectionTest {

    @Test
    public void testNext() {
        MapDirection[] tab = {MapDirection.NORTH,MapDirection.EAST,MapDirection.SOUTH,MapDirection.WEST};
        for (int i = 0; i < 4; i++) {
            assertEquals(tab[i].next(),tab[(i+1) % 4]);
        }
    }

    @Test
    public void testPrevious() {
        MapDirection[] tab = {MapDirection.NORTH,MapDirection.EAST,MapDirection.SOUTH,MapDirection.WEST};
        for (int i = 0; i < 4; i++) {
            assertEquals(tab[i].previous(),tab[(i-1+4) % 4]);
        }
    }


}
