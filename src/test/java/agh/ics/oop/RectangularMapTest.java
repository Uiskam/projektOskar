package agh.ics.oop;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {
    @Test
    public void testMap0(){
        /*
        int[] mapSize = {10,5};//width height
        IWorldMap map = new RectangularMap(mapSize[0],mapSize[1]);
        Vector2d[] positions = {new Vector2d(0,0), new Vector2d(0,1), new Vector2d(0,0), new Vector2d(10,5)};
        Animal[] animals = {new Animal(map,new Vector2d(0,0)), new Animal(map, new Vector2d(0,1)), new Animal(map,new Vector2d(0,0)), new Animal(map,new Vector2d(10,5))};

        boolean[] correct_place = {true,true,false,true};
        for(int i = 0; i < positions.length; i++){
            //place test
            if(i == 2){
                IllegalArgumentException thrown = assertThrows(
                        IllegalArgumentException.class,
                        () -> map.place(new Animal(map,new Vector2d(0,0))), "place fail0"
                );
            }
            else{
                Animal tmp = new Animal(map,positions[i]);
                map.place(tmp);
                assertEquals(map.objectAt(positions[i]), tmp,"place fail");
            }
        }

        for (Animal animal : animals) {
            //isOccpied test and objectAt
            assertTrue(map.isOccupied(animal.getPosition()), "isOccupied0 fail");
            assertNotNull(map.objectAt(animal.getPosition()), "objectAt0 fail");

        }
        assertFalse(map.isOccupied(new Vector2d(1,1)),"isOccupied1 fail");
        assertNull(map.objectAt(new Vector2d(1,1)),"objectAt1 fail");

        //canMove test
        Vector2d[] moves0 = {new Vector2d(0,1),new Vector2d(1,0),new Vector2d(-1,0), new Vector2d(0,-1)};
        boolean[] correctCanMove0 = {false,true,false,false};
        for(int i = 0; i < correctCanMove0.length; i++){
            assertEquals(correctCanMove0[i],map.canMoveTo(moves0[i]),"canMoveFail " + i);
        }
        Vector2d[] moves1 = {new Vector2d(11,5),new Vector2d(10,6),new Vector2d(10,4),new Vector2d(9,5)};
        boolean[] correctCanMove1 = {false,false,true,true};
        for(int i = 0; i < correctCanMove1.length; i++){
            assertEquals(correctCanMove1[i],map.canMoveTo(moves1[i]),"canMoveFail " + i);
        }*/
    }
}
