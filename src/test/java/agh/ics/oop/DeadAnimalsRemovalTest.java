package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadAnimalsRemovalTest {
    @Test
    public void wrappedMapTest0(){
        WrappedMap map = new WrappedMap(3,3,1, 0 ,20);
        Animal testAnimal = new Animal(map,new Vector2d(1,1),1,new Random().ints(32,0,7).toArray(),0);
        //Vector2d[] correctPositionForward = {new Vector2d(1,2), new Vector2d(1,0), new Vector2d(1,1), new Vector2d(1,2), new Vector2d(1,0)};
        map.place(testAnimal);
        testAnimal.energyLoss(2);
        assertEquals(1,map.getObjects().size(),"animal placement error");
        map.removeDeadAnimals();
        assertEquals(0,map.getObjects().size(), "animal removal error ");
    }
}