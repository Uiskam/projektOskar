package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Application;

import static java.lang.System.*;

public class World {
    public static void main(String[] args) {
        try {
            out.println("system wystartował");
            Application.launch(App.class, args);
            out.println("system zakończył działanie");
        } catch (IllegalArgumentException ex) {
            out.println(ex.getMessage() + " hejo");
        }

    }
}

