package agh.ics.oop.gui;

public enum MenuParamEntrance {
    HEIGHT,
    WIDTH,
    JUNGLE_RATIO,
    START_ENERGY,
    MOVE_ENERGY,
    PLANT_ENERGY,
    INITIAL_ANIMAL_NUMBER,
    BORDERLESS_MAP_CHECK,
    ENCLOSED_MAP_CHECK,
    REFRESH_RATE;

    int getIndex() {
        int index = 0;
        for(MenuParamEntrance curVal : MenuParamEntrance.values()){
            if(this == curVal)
                return index;
            index++;
        }
        return -1;
    }

    /*public int dataType(){
        return switch (this) {
            case HEIGHT, WIDTH, START_ENERGY, MOVE_ENERGY, PLANT_ENERGY, INITIAL_ANIMAL_NUMBER -> 0;
            case JUNGLE_RATIO -> 1;
            case BORDERLESS_MAP_CHECK, ENCLOSED_MAP_CHECK -> 2;
        };
    }*/

    @Override
    public String toString(){
        return switch (this) {
            case HEIGHT -> "height: ";
            case WIDTH -> "width: ";
            case JUNGLE_RATIO -> "jungle ratio: ";
            case START_ENERGY -> "start energy: ";
            case MOVE_ENERGY -> "move energy: ";
            case PLANT_ENERGY -> "plant energy: ";
            case INITIAL_ANIMAL_NUMBER -> "initial number of animals: ";
            case BORDERLESS_MAP_CHECK -> "borderless map: ";
            case ENCLOSED_MAP_CHECK -> "enclosed map: ";
            case REFRESH_RATE -> "refresh rate[ms]: ";
        };
    }
}