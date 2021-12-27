package agh.ics.oop.gui;

public enum GuiButtons {
    START,
    STOP;

    int get(){
        return switch (this){
            case START -> 0;
            case STOP -> 1;
        };
    }
}

