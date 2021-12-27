package agh.ics.oop.gui;

public enum GuiButtons {
    START,
    STOP;

    int getIndex(){
        int index = 0;
        for(GuiButtons curVal : GuiButtons.values()){
            if(this == curVal)
                return index;
            index++;
        }
        return -1;
    }

    @Override
    public String toString(){
        return switch (this) {
            case START -> "START";
            case STOP -> "STOP";
        };
    }
}

