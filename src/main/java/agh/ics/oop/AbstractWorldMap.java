package agh.ics.oop;
import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    final protected Map<Vector2d,SortedSet<Animal>> animalMap = new LinkedHashMap<>();
    final protected Map<Vector2d,Grass> grassMap = new LinkedHashMap<>();
    protected final Vector2d[] mapSize = {new Vector2d(0,0), new Vector2d(0,0)};

    public AbstractWorldMap(int width, int height){//generate grass on the filed
        mapSize[0] = new Vector2d(0,0);
        mapSize[1] = new Vector2d(width-1, height-1);
    }

    public abstract boolean canMoveTo(Vector2d position);

    public void place(Animal animal){
        if (!animal.getPosition().follows(mapSize[0]) || !animal.getPosition().precedes(mapSize[1]))
            throw new IllegalArgumentException(animal.getPosition() + " is oustide the map");
        if(!animalMap.containsKey(animal.getPosition())) {
            animalMap.put(animal.getPosition(), new TreeSet<>(new EnergyComparator()));
            animalMap.get(animal.getPosition()).add(animal);
            return;
        }
        throw  new IllegalArgumentException(animal.getPosition() + " is already occupied");
    }

    public void placeChild(Animal child){
        if(!animalMap.containsKey(child.getPosition())) {
            animalMap.put(child.getPosition(), new TreeSet<>(new EnergyComparator()));
        }
        animalMap.get(child.getPosition()).add(child);
    }

    public boolean isOccupied(Vector2d position){
        return animalMap.containsKey(position) || grassMap.containsKey(position);
    }

    public Object objectAt(Vector2d position){
        if(animalMap.containsKey(position)){
            return animalMap.get(position).first();
        }
        if(grassMap.containsKey(position)){
            return grassMap.get(position);
        }
        return null;
    }

    public String toString(){
        return new MapVisualizer(this).draw(mapSize[0],mapSize[1]);
    }

    public Vector2d[] getMapSize(){
        return mapSize;
    };

    public void removeDeadAnimals(){
        animalMap.keySet().forEach(position ->
                animalMap.get(position).removeIf(animal -> animal.getEnergy() <= 0));
        animalMap.keySet().removeIf(position -> animalMap.get(position).isEmpty());
    }

    public HashMap<Vector2d,IMapElement> getObjects(){
        HashMap<Vector2d,IMapElement> objects = new HashMap<>(this.grassMap);
        animalMap.values().forEach(animalSet -> objects.put(animalSet.first().getPosition(), animalSet.first()));
        return objects;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        animalMap.get(oldPosition).remove(animal);
        if(animalMap.get(oldPosition).isEmpty())
            animalMap.remove(oldPosition);
        if(!animalMap.containsKey(newPosition))
            animalMap.put(newPosition,new TreeSet<>(new EnergyComparator()));
        animalMap.get(newPosition).add(animal);
    }




}
