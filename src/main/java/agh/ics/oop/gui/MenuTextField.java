package agh.ics.oop.gui;

public enum MenuTextField {
    HEIGHT,
    WIDTH,
    JUNGLE_RATIO,
    START_ENERGY,
    MOVE_ENERGY,
    PLANT_ENERGY,
    INITIAL_ANIMAL_NUMBER;

    int get() {
        return switch (this) {
            case HEIGHT -> 0;
            case WIDTH -> 1;
            case JUNGLE_RATIO -> 3;
            case START_ENERGY -> 4;
            case MOVE_ENERGY -> 5;
            case PLANT_ENERGY -> 6;
            case INITIAL_ANIMAL_NUMBER -> 7;
        };
    }
}
