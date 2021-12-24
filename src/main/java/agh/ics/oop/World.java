package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Application;
import org.jetbrains.annotations.NotNull;
import java.util.SortedMap;
import java.util.*;

import static java.lang.System.*;

public class World {
    public static void main(String[] args) {
        WrappedMap map = new WrappedMap(3,3,1,8);
        Animal testAnimal = new Animal(map,new Vector2d(1,1),1,new Random().ints(32,0,7).toArray());
        map.place(testAnimal);
        for(int i = 0; i < 3; i++) {
            System.out.println(new MapVisualizer(map).draw(map.getMapSize()[0], map.getMapSize()[1]));
            testAnimal.move(4);

        }


        /*try {
            out.println("system wystartował");
            Application.launch(App.class, args);
            out.println("system zakończył działanie");
        } catch (IllegalArgumentException ex) {
            out.println(ex.getMessage() + " hejo");
        }*/

    }
}

