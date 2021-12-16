package agh.ics.oop;

import java.util.ArrayList;

public class OptionsParser {
    public static MoveDirection[] parse(String[] arg) {
        int howManyAreCorrect = 0;
        for (String cur : arg) {
            if (cur.equals("f") || cur.equals("b") || cur.equals("r") || cur.equals("l")
                    || cur.equals("forward") || cur.equals("backward") || cur.equals("left") || cur.equals("right")) {
                howManyAreCorrect++;
            }
            else {
                throw new IllegalArgumentException(cur + " is not legal move specification");
            }
        }
        MoveDirection[] result = new MoveDirection[howManyAreCorrect];
        int resultIndeks = 0;
        for (String cur : arg) {
            if (cur.equals("f") || cur.equals("b") || cur.equals("r") || cur.equals("l")
                    || cur.equals("forward") || cur.equals("backward") || cur.equals("left") || cur.equals("right")) {
                switch (cur) {
                    case "f", "forward" -> result[resultIndeks++] = MoveDirection.FORWARD;
                    case "b", "backward" -> result[resultIndeks++] = MoveDirection.BACKWARD;
                    case "l", "left" -> result[resultIndeks++] = MoveDirection.LEFT;
                    case "r", "right" -> result[resultIndeks++] = MoveDirection.RIGHT;
                }
            }
        }
        return result;
    }
}
