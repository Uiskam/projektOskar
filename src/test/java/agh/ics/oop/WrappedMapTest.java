package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.Random;

public class WrappedMapTest {
    @Test
    public void wrappedMapTest0(){
        WrappedMap map = new WrappedMap(3,3,1,8);
        Animal testAnimal = new Animal(map,new Vector2d(1,1),1,new Random().ints(32,0,7).toArray());
        map.place(testAnimal);
        for(int i = 0; i < 10; i++) {
            System.out.println(new MapVisualizer(map).draw(map.getMapSize()[0], map.getMapSize()[1]));
            testAnimal.move(4);

        }
    }
}
