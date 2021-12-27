package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RectangularMap extends AbstractWorldMap implements IWorldMap{
    //private final Vector2d[] mapSize = {new Vector2d((int)10e9,(int)10e9), new Vector2d((int)-10e9,(int)-10e9)};

    public RectangularMap(int width, int height, double jungleRatio, int givenEnergyMove, int givenPlantEnergy) {
        super(width, height, jungleRatio, givenEnergyMove, givenPlantEnergy);
    }


    public boolean canMoveTo(Vector2d position){
        return position.follows(mapSize[0]) && position.precedes(mapSize[1]);
    }



}
