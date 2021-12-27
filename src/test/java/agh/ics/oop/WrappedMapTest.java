package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

public class WrappedMapTest {
    private void changeDirectionTo(MapDirection direction, Animal animal){
        while (!animal.toString().equals(direction.toString())){
            animal.move(1);
        }
    }
    @Test
    public void wrappedMapTest0(){
        WrappedMap map = new WrappedMap(3,3,1.0/8.0, 0 , 20);
        Animal testAnimal = new Animal(map,new Vector2d(1,1),1,
                new Random().ints(32,0,7).toArray(),0);
        Vector2d[] correctPositionForward = {new Vector2d(1,2), new Vector2d(1,0), new Vector2d(1,1), new Vector2d(1,2), new Vector2d(1,0)};
        map.place(testAnimal);
        changeDirectionTo(MapDirection.NORTH, testAnimal);

        for(int i = 0; i < 5; i++) {
            testAnimal.move(0);
            assertEquals(correctPositionForward[i], testAnimal.getPosition(), "wrapping forward error: " + correctPositionForward[i] + " " + testAnimal.getPosition());
        }
    }

    @Test
    public void wrappedMapTest1(){
        WrappedMap map = new WrappedMap(3,3,1.0/8.0,0,20);
        Animal testAnimal = new Animal(map,new Vector2d(1,1),1,
                new Random().ints(32,0,7).toArray(),0);
        map.place(testAnimal);
        changeDirectionTo(MapDirection.NORTH, testAnimal);
        Vector2d[] correctPositionBackward = {new Vector2d(1,0), new Vector2d(1,2), new Vector2d(1,1), new Vector2d(1,0), new Vector2d(1,2)};
        for(int i = 0; i < 5; i++) {
            testAnimal.move(4);
            assertEquals(correctPositionBackward[i], testAnimal.getPosition(), "wrapping backward error: " + correctPositionBackward[i] + " " + testAnimal.getPosition());
        }
    }

    @Test
    public void wrappedMapTest2(){
        WrappedMap map = new WrappedMap(3,3,1.0/8.0,0,20);
        Animal testAnimal = new Animal(map,new Vector2d(1,1),1,
                new Random().ints(32,0,7).toArray(),0);
        map.place(testAnimal);
        changeDirectionTo(MapDirection.WEST, testAnimal);
        Vector2d[] correctPositionBackward = {new Vector2d(0,1), new Vector2d(2,1), new Vector2d(1,1), new Vector2d(0,1), new Vector2d(2,1)};
        for(int i = 0; i < 5; i++) {
            testAnimal.move(0);
            assertEquals(correctPositionBackward[i], testAnimal.getPosition(), "wrapping left error: " + correctPositionBackward[i] + " " + testAnimal.getPosition());
        }
    }

    @Test
    public void wrappedMapTest3(){
        WrappedMap map = new WrappedMap(3,3,1.0/8.0,0,20);
        Animal testAnimal = new Animal(map,new Vector2d(1,1),1,
                new Random().ints(32,0,7).toArray(),0);
        map.place(testAnimal);
        changeDirectionTo(MapDirection.WEST, testAnimal);
        Vector2d[] correctPositionBackward = {new Vector2d(2,1), new Vector2d(0,1), new Vector2d(1,1), new Vector2d(2,1), new Vector2d(0,1)};
        for(int i = 0; i < 5; i++) {
            testAnimal.move(4);
            assertEquals(correctPositionBackward[i], testAnimal.getPosition(), "wrapping right error: " + correctPositionBackward[i] + " " + testAnimal.getPosition());
        }
    }
}
