package agh.ics.oop;

import java.util.*;

import static java.lang.Math.sqrt;

public class WrappedMap extends AbstractWorldMap implements IWorldMap{

    public WrappedMap(int width, int height, int jungleRatio, int savannaRatio) {
        super(width, height, jungleRatio, savannaRatio);
    }

    public Vector2d wrapVector(Vector2d position){
        return new Vector2d((position.x + mapSize[1].x+1) % (mapSize[1].x+1), (position.y + mapSize[1].y+1) % (mapSize[1].y+1));
    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }
}
