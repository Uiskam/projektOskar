package agh.ics.oop;
import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    //protected Vector2d[] mapSize = {new Vector2d((int)10e9,(int)10e9), new Vector2d((int)-10e9,(int)-10e9)};
    final protected Map<Vector2d,Animal> animalMap = new LinkedHashMap<>();
    //mapSize[0] - lower left corner mapSize[1] - upper right corner

    public String toString(){
        Vector2d[] tmp = getMapSize();
        return new MapVisualizer(this).draw(tmp[0],tmp[1]);
    }
    public abstract  Vector2d[] getMapSize();

    public boolean canMoveTo(Vector2d position){
        return !animalMap.containsKey(position);
    }

    public void place(Animal animal){
        if(!animalMap.containsKey(animal.getPosition())) {
            animalMap.put(animal.getPosition(), animal);
            return;
        }
        throw  new IllegalArgumentException(animal.getPosition() + " is already occupied");
    }

    public boolean isOccupied(Vector2d position){
        return animalMap.containsKey(position);
    }

    public Object objectAt(Vector2d position){
        if(animalMap.containsKey(position)){
            return animalMap.get(position);
        }
        return null;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        animalMap.put(newPosition, animalMap.remove(oldPosition));
    }

    public abstract HashMap<Vector2d,IMapElement> getObjects();
}
