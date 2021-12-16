package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
    @Test
    public void testAnimal_0() {
        MapBoundary tmp0 = new MapBoundary();
        IWorldMap tmp = new GrassField(0);
        String[] test_sq0 = {"forward", "b", "left", "left", "forward" };

        MoveDirection[] correctMoves = {MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.LEFT, MoveDirection.LEFT, MoveDirection.FORWARD};
        String[] correctDirection = {"N", "N", "W", "S", "S" };
        //String[] correctPosition = {"(2,3)", "(2,2)", "(2,2)", "(2,2)", "(2,1)" };
        Vector2d[] correctPosition = {new Vector2d(2,3), new Vector2d(2,2), new Vector2d(2,2), new Vector2d(2,2), new Vector2d(2,1)};

        MoveDirection[] moves = OptionsParser.parse(test_sq0);
        for (int i = 0; i < moves.length; i++) { //czy prawidłowe odczytanie wejścia
            assertEquals(moves[i], correctMoves[i]);
        }
        Animal tiger = new Animal(tmp,new Vector2d(2,2));
        for (int i = 0; i < moves.length; i++) {//tutaj sprawdzam czy dobrze chodzi, orientację i nie wychodznie poza mapę
            tiger.move(moves[i]);
            assertEquals(tiger.toString(), correctDirection[i]);
            //System.out.println(tiger.getPosition());
            assertEquals(tiger.getPosition(),correctPosition[i],"tutaj");
        }
    }

    @Test
    public void testAnimal_1() {
        IWorldMap tmp = new RectangularMap(10,4);
        String[] test_sq1 = {"right", "l", "f", "f", "f", "f", "f", "left", "b", "forward", "backward", "b", "r" };

        MoveDirection[] correctMoves = {MoveDirection.RIGHT, MoveDirection.LEFT, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.LEFT, MoveDirection.BACKWARD, MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.BACKWARD, MoveDirection.RIGHT};
        //String[] correctDirection = {"Wschód", "Północ", "Północ", "Północ", "Północ", "Północ", "Północ", "Zachód", "Zachód", "Zachód", "Zachód", "Zachód", "Północ" };
        String[] correctDirection = {"E", "N", "N", "N", "N", "N", "N", "W", "W", "W", "W", "W", "N" };
        //Vector2d[] correctPosition = {"(2,2)", "(2,2)", "(2,3)", "(2,4)", "(2,4)", "(2,4)", "(2,4)", "(2,4)", "(3,4)", "(2,4)", "(3,4)", "(4,4)", "(4,4)" };
        Vector2d[] correctPosition = {new Vector2d(2,2), new Vector2d(2,2), new Vector2d(2,3), new Vector2d(2,4), new Vector2d(2,4), new Vector2d(2,4), new Vector2d(2,4), new Vector2d(2,4), new Vector2d(3,4), new Vector2d(2,4),  new Vector2d(3,4), new Vector2d(4,4), new Vector2d(4,4)};
        MoveDirection[] moves = OptionsParser.parse(test_sq1);
        //IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> OptionsParser.parse(test_sq1), "praser fail");
        for (int i = 0; i < moves.length; i++) { //czy prawidłowe odczytanie wejścia
            assertEquals(moves[i], correctMoves[i]);
        }
        Animal tiger = new Animal(tmp,new Vector2d(2,2));
        for (int i = 0; i < moves.length; i++) {//tutaj sprawdzam czy dobrze chodzi, orientację i nie wychodznie poza mapę
            tiger.move(moves[i]);
            assertEquals(tiger.toString(), correctDirection[i],"tylko nie to");
            assertEquals(tiger.getPosition(),correctPosition[i],"tutaj");

        }

    }


}
