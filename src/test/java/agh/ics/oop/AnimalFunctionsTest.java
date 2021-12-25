package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalFunctionsTest {
    private void changeDirectionTo(MapDirection direction, Animal animal){
        while (!animal.toString().equals(direction.toString())){
            animal.move(1);
        }
    }

    @Test
    public void animalRotationTest() {
        Animal testAnimal = new Animal(map,new Vector2d(5,5),1,new Random().ints(32,0,7).toArray());
        changeDirectionTo(MapDirection.NORTH,testAnimal);

        for(int i = 0; i < 7; i++){
            if(i != 0 && i != 4){

            }
        }
    }
    @Test
    public void animalMovementTest0(){
        WrappedMap map = new WrappedMap(10,10,1,8);
        Animal testAnimal = new Animal(map,new Vector2d(5,5),1,new Random().ints(32,0,7).toArray());
        changeDirectionTo(MapDirection.NORTH,testAnimal);
        map.place(testAnimal);
        testAnimal.move(0);
        assertEquals(new Vector2d(5,6),testAnimal.getPosition(),"North error");
        testAnimal.move(4);
        assertEquals(new Vector2d(5,5),testAnimal.getPosition(),"North backwards error");

        testAnimal.move(1);
        assertEquals(MapDirection.NORTH_EAST.toString(),testAnimal.toString(), "turn error");
    }
}
