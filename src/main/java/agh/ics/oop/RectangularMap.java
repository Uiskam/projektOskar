package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RectangularMap extends AbstractWorldMap implements IWorldMap{
    private final Vector2d[] mapSize = {new Vector2d((int)10e9,(int)10e9), new Vector2d((int)-10e9,(int)-10e9)};
    //mapSize[0] = (0,0), mapSize[1] = (width,height)
    //private final List<Animal> animalMap = new ArrayList<>();
    public RectangularMap(int width, int height){
        this.mapSize[0] = new Vector2d(0,0);
        this.mapSize[1] = new Vector2d(width,height);
    }

    /*public String toString(){
        return new MapVisualizer(this).draw(mapSize[0],mapSize[1]);
    }*/
    public Vector2d[] getMapSize(){
        return mapSize;
    }
    public boolean canMoveTo(Vector2d position){
        if (!position.follows(mapSize[0]) || !position.precedes(mapSize[1]))
            return false;
        return super.canMoveTo(position);
    }
    public void place(Animal animal){
        Vector2d position = animal.getPosition();
        if (!position.follows(mapSize[0]) || !position.precedes(mapSize[1]))
            throw new IllegalArgumentException(position + " is oustide the map thus object cannot be placed");
        super.place(animal);
    }

    @Override
    public HashMap<Vector2d,IMapElement> getObjects(){
        return new HashMap<>(this.animalMap);
    }
    @Override
    public void removeElemMapBoundary(IMapElement object) {
        ;
    }

    @Override
    public void addElemMapBoundary(IMapElement object) {
        ;
    }
}
