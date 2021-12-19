package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EngineTest {
    @Test
    public void testEngine_0() {
        String[] test_sq0 = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        MoveDirection[] correctMoves = {MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.RIGHT, MoveDirection.LEFT, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.RIGHT, MoveDirection.RIGHT,
                MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD};
        Vector2d[] correctPosition = {new Vector2d(2, 3), new Vector2d(3, 3), new Vector2d(2, 3), new Vector2d(3, 3),
                new Vector2d(2, 3), new Vector2d(3, 3), new Vector2d(2, 3), new Vector2d(3, 3), new Vector2d(2, 2),
                new Vector2d(3, 4), new Vector2d(2, 1), new Vector2d(3, 5), new Vector2d(2, 0), new Vector2d(3, 5),
                new Vector2d(2, 0), new Vector2d(3, 5)};

        IWorldMap map = new RectangularMap(10,5);
        Vector2d[] poistions = {new Vector2d(2,2), new Vector2d(3,4), new Vector2d(2,2)};

        MoveDirection[] moves = OptionsParser.parse(test_sq0);
        for (int i = 0; i < moves.length; i++) { //czy prawidłowe odczytanie wejścia
            assertEquals(moves[i], correctMoves[i],"input read");
        }
        final List<Animal> animal = new ArrayList<>();
        for (int i = 0; i < poistions.length; i++){//sprawdzanie place
            Animal tmp = new Animal(map,poistions[i],10,1);
            if (i <= 1) {
                map.place(tmp);
                animal.add(tmp);
            }
            else {
                IllegalArgumentException thrown = assertThrows(
                        IllegalArgumentException.class,
                        () -> map.place(tmp), "place fail"
                );
            }

        }
        for (int i = 0; i < moves.length; i++) {//sprawdzanie porpawności ruchów
            animal.get(i % animal.size()).move();
            assertEquals(animal.get(i % animal.size()),map.objectAt(correctPosition[i]),"corraction of moves fail");
        }

    }
}
