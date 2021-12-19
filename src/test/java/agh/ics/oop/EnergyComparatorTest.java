package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnergyComparatorTest {
    @Test
    public void cmpTest() {
        IWorldMap tmpMap = new RectangularMap(10, 10);
        Vector2d tmpVector = new Vector2d(1, 1);
        Animal[] tigers = {new Animal(tmpMap, tmpVector, 10, 2),
                new Animal(tmpMap, tmpVector, 20, 1)};
        SortedSet<Animal> testSet = new TreeSet<>(new EnergyComparator());
        testSet.addAll(Arrays.asList(tigers));
        assertEquals(tigers[1], testSet.first(), "Energy comparator not working properly");

    }
}
