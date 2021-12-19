package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
    @Test
    public void testAnimal_0() {
        new Animal(new RectangularMap(10,10), new Vector2d(5,5), 0,0);
    }


}
