package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AnimalFunctionsTest {
    private void changeDirectionTo(MapDirection direction, Animal animal) {
        while (!animal.toString().equals(direction.toString())) {
            animal.move(1);
        }
    }

    @Test
    public void animalPlacementAndPositionChangeTest() {
        RectangularMap map = new RectangularMap(10, 10, 1, 1);
        Animal testAnimal = new Animal(map, new Vector2d(5, 5), 10, new Random().ints(32, 0, 7).toArray());
        map.place(testAnimal);
        HashMap<Vector2d, IMapElement> objects = map.getObjects();
        assertEquals(testAnimal, objects.get(testAnimal.getPosition()));
    }

    @Test
    public void animalRotationTest() {
        RectangularMap map = new RectangularMap(10, 10, 1, 1);
        Animal testAnimal = new Animal(map, new Vector2d(5, 5), 1, new Random().ints(32, 0, 7).toArray());
        MapDirection[] correctDirections = {MapDirection.NORTH_EAST, MapDirection.SOUTH_EAST, MapDirection.WEST,
                MapDirection.SOUTH_EAST, MapDirection.NORTH_EAST, MapDirection.NORTH};
        changeDirectionTo(MapDirection.NORTH, testAnimal);
        int correctDirectionIndex = 0;
        for (int i = 0; i <= 7; i++) {
            if (i != 0 && i != 4) {
                testAnimal.move(i);
                assertEquals(correctDirections[correctDirectionIndex++].toString(), testAnimal.toString(), "rotation error");
            }
        }
    }

    @Test
    public void animalMovementTest0() {
        WrappedMap map = new WrappedMap(10, 10, 1, 8);
        Animal testAnimal = new Animal(map, new Vector2d(5, 5), 1, new Random().ints(32, 0, 7).toArray());
        map.place(testAnimal);

        changeDirectionTo(MapDirection.NORTH, testAnimal);
        testAnimal.move(0);
        assertEquals(new Vector2d(5, 6), testAnimal.getPosition(), "North error");
        testAnimal.move(4);
        assertEquals(new Vector2d(5, 5), testAnimal.getPosition(), "North backwards error");

        testAnimal.move(1);
        assertEquals(MapDirection.NORTH_EAST.toString(), testAnimal.toString(), "turn error");
        testAnimal.move(0);
        assertEquals(new Vector2d(6, 6), testAnimal.getPosition(), "NorthEast error");
        testAnimal.move(4);
        assertEquals(new Vector2d(5, 5), testAnimal.getPosition(), "NorthEast backwards error");

        testAnimal.move(1);
        assertEquals(MapDirection.EAST.toString(), testAnimal.toString(), "turn error");
        testAnimal.move(0);
        assertEquals(new Vector2d(6, 5), testAnimal.getPosition(), "East error");
        testAnimal.move(4);
        assertEquals(new Vector2d(5, 5), testAnimal.getPosition(), "East backwards error");

        testAnimal.move(1);
        assertEquals(MapDirection.SOUTH_EAST.toString(), testAnimal.toString(), "turn error");
        testAnimal.move(0);
        assertEquals(new Vector2d(6, 4), testAnimal.getPosition(), "SouthEast error");
        testAnimal.move(4);
        assertEquals(new Vector2d(5, 5), testAnimal.getPosition(), "SouthEast backwards  error");

        testAnimal.move(1);
        assertEquals(MapDirection.SOUTH.toString(), testAnimal.toString(), "turn error");
        testAnimal.move(0);
        assertEquals(new Vector2d(5, 4), testAnimal.getPosition(), "South diagonal error");
        testAnimal.move(4);
        assertEquals(new Vector2d(5, 5), testAnimal.getPosition(), "South backwards error");

        testAnimal.move(1);
        assertEquals(MapDirection.SOUTH_WEST.toString(), testAnimal.toString(), "turn error");
        testAnimal.move(0);
        assertEquals(new Vector2d(4, 4), testAnimal.getPosition(), "SouthWest diagonal error");
        testAnimal.move(4);
        assertEquals(new Vector2d(5, 5), testAnimal.getPosition(), "SouthWest backwards error");

        testAnimal.move(1);
        assertEquals(MapDirection.WEST.toString(), testAnimal.toString(), "turn error");
        testAnimal.move(0);
        assertEquals(new Vector2d(4, 5), testAnimal.getPosition(), "West diagonal error");
        testAnimal.move(4);
        assertEquals(new Vector2d(5, 5), testAnimal.getPosition(), "West backwards error");

        testAnimal.move(1);
        assertEquals(MapDirection.NORTH_WEST.toString(), testAnimal.toString(), "turn error");
        testAnimal.move(0);
        assertEquals(new Vector2d(4, 6), testAnimal.getPosition(), "NorthWest diagonal error");
        testAnimal.move(4);
        assertEquals(new Vector2d(5, 5), testAnimal.getPosition(), "NorthWest backwards error");
    }

    @Test
    public void eatingTest() {
        WrappedMap map = new WrappedMap(10, 10, 1, 8);
        Animal testAnimal = new Animal(map, new Vector2d(5, 5), 1, new Random().ints(32, 0, 7).toArray());
        map.place(testAnimal);
        map.addGrass();
        HashMap<Vector2d, IMapElement> objects = map.getObjects();
        Vector2d grasPosition = new Vector2d(0, 0);
        for (Vector2d position : objects.keySet()) {
            if (objects.get(position) instanceof Grass) {
                grasPosition = position;
            }
        }
        Vector2d positionDifference = new Vector2d(Math.abs(testAnimal.getPosition().x - grasPosition.x),
                Math.abs(testAnimal.getPosition().y - grasPosition.y));
        changeDirectionTo(MapDirection.NORTH, testAnimal);
        for (int i = 0; i < positionDifference.y; i++) {
            if (grasPosition.y > testAnimal.getPosition().y) {
                testAnimal.move(0);
            } else {
                testAnimal.move(4);
            }
        }

        changeDirectionTo(MapDirection.EAST, testAnimal);
        for (int i = 0; i < positionDifference.x; i++) {
            if (grasPosition.x > testAnimal.getPosition().x) {
                testAnimal.move(0);
            } else {
                testAnimal.move(4);
            }
        }

        map.eatGrass(10);
        testAnimal.move(4);
        objects = map.getObjects();
        assertNull(objects.get(grasPosition));
    }

    @Test
    public void reproductionTest() {
        RectangularMap map = new RectangularMap(10, 10, 1, 1);
        int[] genotype = new int[32];
        for (int i = 0; i < 32; i++) {
            genotype[i] = 0;
        }
        Animal testAnimal0 = new Animal(map, new Vector2d(5, 5), 10, genotype);
        for(int i = 0; i < 32; i++){
            genotype[i] = 3;
        }
        Animal testAnimal1 = new Animal(map, new Vector2d(5, 6), 10, genotype);
        map.place(testAnimal0);
        map.place(testAnimal1);
        changeDirectionTo(MapDirection.EAST, testAnimal1);
        testAnimal1.move(0);
        map.animalReproduction(10);
        testAnimal0.move(0);
        testAnimal1.move(0);
        HashMap<Vector2d, IMapElement> objects = map.getObjects();
        if(objects.containsKey(new Vector2d(5,5))){
            Animal child = (Animal) objects.get(new Vector2d(5, 5));
        }
        else{
            System.out.println("fdwsfdsgfdsagfdslkhbgfdslk");
            ;//assertEquals(1,0);
        }

    }
}
