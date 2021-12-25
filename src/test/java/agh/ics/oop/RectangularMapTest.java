package agh.ics.oop;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {
    @Test
    private void changeDirectionTo(MapDirection direction, Animal animal){
        while (!animal.toString().equals(direction.toString())){
            animal.move(1);
        }
    }

    @Test
    public void rectMapTest0(){
        RectangularMap map = new RectangularMap(3,3,1,8);
        Animal testAnimal = new Animal(map,new Vector2d(1,1),1,new Random().ints(32,0,7).toArray());
        Vector2d[] correctPositionForward = {new Vector2d(1,2), new Vector2d(1,2), new Vector2d(1,2), new Vector2d(1,2), new Vector2d(1,2)};
        map.place(testAnimal);
        changeDirectionTo(MapDirection.NORTH, testAnimal);

        for(int i = 0; i < 5; i++) {
            testAnimal.move(0);
            //System.out.println(correctPositionForward[i] + " " + testAnimal.getPosition());
            assertEquals(correctPositionForward[i], testAnimal.getPosition(), " forward error: " + correctPositionForward[i] + " " + testAnimal.getPosition());
        }
    }

    @Test
    public void rectMapTest1(){
        RectangularMap map = new RectangularMap(3,3,1,8);
        Animal testAnimal = new Animal(map,new Vector2d(1,1),1,new Random().ints(32,0,7).toArray());
        Vector2d[] correctPositionForward = {new Vector2d(1,0), new Vector2d(1,0), new Vector2d(1,0), new Vector2d(1,0), new Vector2d(1,0)};
        map.place(testAnimal);
        changeDirectionTo(MapDirection.NORTH, testAnimal);

        for(int i = 0; i < 5; i++) {
            testAnimal.move(4);
            //System.out.println(correctPositionForward[i] + " " + testAnimal.getPosition());
            assertEquals(correctPositionForward[i], testAnimal.getPosition(), " forward error: " + correctPositionForward[i] + " " + testAnimal.getPosition());
        }
    }

    @Test
    public void rectMapTest2(){
        RectangularMap map = new RectangularMap(3,3,1,8);
        Animal testAnimal = new Animal(map,new Vector2d(1,1),1,new Random().ints(32,0,7).toArray());
        Vector2d[] correctPositionForward = {new Vector2d(0,1), new Vector2d(0,1), new Vector2d(0,1), new Vector2d(0,1), new Vector2d(0,1)};
        map.place(testAnimal);
        changeDirectionTo(MapDirection.EAST, testAnimal);

        for(int i = 0; i < 5; i++) {
            testAnimal.move(4);
            //System.out.println(correctPositionForward[i] + " " + testAnimal.getPosition());
            assertEquals(correctPositionForward[i], testAnimal.getPosition(), " left error: " + correctPositionForward[i] + " " + testAnimal.getPosition());
        }
    }

    @Test
    public void rectMapTest3(){
        RectangularMap map = new RectangularMap(3,3,1,8);
        Animal testAnimal = new Animal(map,new Vector2d(1,1),1,new Random().ints(32,0,7).toArray());
        Vector2d[] correctPositionForward = {new Vector2d(2,1), new Vector2d(2,1), new Vector2d(2,1), new Vector2d(2,1), new Vector2d(2,1)};
        map.place(testAnimal);
        changeDirectionTo(MapDirection.EAST, testAnimal);

        for(int i = 0; i < 5; i++) {
            testAnimal.move(0);
            //System.out.println(correctPositionForward[i] + " " + testAnimal.getPosition());
            assertEquals(correctPositionForward[i], testAnimal.getPosition(), " right error: " + correctPositionForward[i] + " " + testAnimal.getPosition());
        }
    }
}
