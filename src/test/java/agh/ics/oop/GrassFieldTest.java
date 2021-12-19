package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrassFieldTest {
    @Test
    public void testMapGrass0(){
        /*int[] mapSize = {10,5};//width height
        MapBoundary tmp0 = new MapBoundary();
        final IWorldMap map = new GrassField(10);
        Vector2d[] positions = {new Vector2d(0,0), new Vector2d(0,1), new Vector2d(0,0), new Vector2d(10,5)};
        Animal[] animals = {new Animal(map,new Vector2d(0,0)), new Animal(map, new Vector2d(0,1)), new Animal(map,new Vector2d(0,0)), new Animal(map,new Vector2d(10,5))};

        final boolean[] correct_place = {true,true,false,true};
        for(int i = 0; i < positions.length; i++){
            //place test
            if(i == 2){
                IllegalArgumentException thrown = assertThrows(
                        IllegalArgumentException.class,
                        () -> map.place(new Animal(map,new Vector2d(0,0))), "place fail0"
                );
            }
            else{
                map.place(new Animal(map,positions[i]));
                assertNotNull(map.objectAt(positions[i]),positions[i].toString() + i);
            }
        }

        for (Animal animal : animals) {
            //isOccupied test and objectAt
            assertTrue(map.isOccupied(animal.getPosition()), "isOccupied0 fail");
            assertNotNull(map.objectAt(animal.getPosition()), "objectAt0 fail");

        }

        //canMove test
        Vector2d[] moves0 = {new Vector2d(0,1),new Vector2d(1,0),new Vector2d(-1,0), new Vector2d(0,-1)};
        boolean[] correctCanMove0 = {false,true,true,true};
        for(int i = 0; i < correctCanMove0.length; i++){
            assertEquals(correctCanMove0[i],map.canMoveTo(moves0[i]),"canMoveFail " + i);
        }
        Vector2d[] moves1 = {new Vector2d(11,5),new Vector2d(10,6),new Vector2d(10,4),new Vector2d(9,5)};
        for(int i = 0; i < moves1.length; i++){
            assertTrue(map.canMoveTo(moves1[i]),"canMoveFail " + i);
        }*/
    }
}
