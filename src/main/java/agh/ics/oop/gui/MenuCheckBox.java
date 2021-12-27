package agh.ics.oop.gui;

public enum MenuCheckBox {
    BORDERLESS_MAP,
    ENCLOSED_MAP;

    int get(){
        return switch (this) {
            case BORDERLESS_MAP -> 0;
            case ENCLOSED_MAP -> 1;
        }
    }
}
