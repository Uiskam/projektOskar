package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDirectionTest {

    @Test
    public void testNext() {
        MapDirection[] tab = {MapDirection.NORTH, MapDirection.NORTH_EAST,MapDirection.EAST,MapDirection.SOUTH_EAST,MapDirection.SOUTH,MapDirection.SOUTH_WEST,MapDirection.WEST,MapDirection.NORTH_WEST};
        for (int i = 0; i < 8; i++) {
            assertEquals(tab[i].next(),tab[(i+1) % 8]);
        }
    }

    @Test
    public void testPrevious() {
        MapDirection[] tab = {MapDirection.NORTH, MapDirection.NORTH_EAST,MapDirection.EAST,MapDirection.SOUTH_EAST,MapDirection.SOUTH,MapDirection.SOUTH_WEST,MapDirection.WEST,MapDirection.NORTH_WEST};
        for (int i = 0; i < 8; i++) {
            assertEquals(tab[i].previous(),tab[(i-1+8) % 8]);
        }
    }


}
