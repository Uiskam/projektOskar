package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Application;
import org.jetbrains.annotations.NotNull;
import java.util.SortedMap;
import java.util.*;

import static java.lang.System.*;

public class World {
    public static void main(String[] args) {
        try {
            out.println("system wystartował");
            Application.launch(App.class, args);
            out.println("system zakończył działanie");
        } catch (IllegalArgumentException ex) {
            out.println(ex.getMessage());
        }



        /*out.println("system wystartował");
        String[] test_sq_lab4 = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        if(true || false) {
            MoveDirection[] directions = OptionsParser.parse(args);
            for(String x : args) out.print(x);
            IWorldMap map = new RectangularMap(10, 5);
            Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
            IEngine engine = new SimulationEngine(directions, map, positions);
            out.println(engine);
            engine.run();
            out.println(engine);
            out.println("system zakończył działanie");
        }
        else {
            MoveDirection[] directions = OptionsParser.parse(test_sq_lab4);
            IWorldMap map = new GrassField(10);
            Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4), new Vector2d(2, 2)};
            IEngine engine = new SimulationEngine(directions, map, positions);
            out.println(engine);
            engine.run();
            out.println(engine);
        }*/
    }

    public static void run(MoveDirection[] input) {
        for (MoveDirection arg : input) {
            switch (arg) {
                case FORWARD -> System.out.println("Zwierzak idzie do przodu");
                case BACKWARD -> System.out.println("Zwierzak idzie do tyłu");
                case RIGHT -> System.out.println("Zwierzak skręca w prawo");
                case LEFT -> System.out.println("Zierzak skręca w lewo");
            }
        }
    }

    static MoveDirection[] convert(String[] args) {
        int howManyAreCorrect = 0;
        for (String cur : args) {
            if (cur.equals("f") || cur.equals("b") || cur.equals("r") || cur.equals("l")) {
                howManyAreCorrect++;
            }
        }
        MoveDirection[] result = new MoveDirection[howManyAreCorrect];
        int resultIndeks = 0;
        for (String cur : args) {
            if (cur.equals("f") || cur.equals("b") || cur.equals("r") || cur.equals("l")) {
                switch (cur) {
                    case "f" -> result[resultIndeks++] = MoveDirection.FORWARD;
                    case "b" -> result[resultIndeks++] = MoveDirection.BACKWARD;
                    case "r" -> result[resultIndeks++] = MoveDirection.RIGHT;
                    case "l" -> result[resultIndeks++] = MoveDirection.LEFT;
                }
            }
        }
        return result;
    }

}

