package agh.ics.oop;

import java.util.*;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap implements IWorldMap{
    final private Map<Vector2d,Grass> grassMap = new LinkedHashMap<>();
    final private MapBoundary mapBoundary = new MapBoundary();
    public GrassField(int n){//generate grass on the filed
        Random rand = new Random();
        Vector2d tmp;
        for(int i = 0; i < n; i++){
            do{
                tmp = new Vector2d(rand.nextInt((int)sqrt(n*10)+1),rand.nextInt((int)sqrt(n*10)+1));
            }while(grassMap.containsKey(tmp));
            Grass newGrass = new Grass(tmp);
            grassMap.put(newGrass.getPosition(), newGrass);
            mapBoundary.addObject(newGrass);
        }

    }


    public Vector2d[] getMapSize(){
        return mapBoundary.getSize();
    }
    @Override
    public void place(Animal animal){
        super.place(animal);
        mapBoundary.addObject(animal);
    }
    public boolean isOccupied(Vector2d position){
        if(super.isOccupied(position)) {
            return true;
        }
        return grassMap.containsKey(position);

    }

    public Object objectAt(Vector2d position){
        Object tmp = super.objectAt(position);
        if(tmp != null)
            return tmp;
        else {
            if(grassMap.containsKey(position)) return grassMap.get(position);
            return null;
        }
    }

    @Override
    public HashMap<Vector2d,IMapElement> getObjects(){
        HashMap<Vector2d,IMapElement> tmp = new HashMap<>(this.grassMap);
        tmp.putAll(this.animalMap);
        return tmp;
    }

    @Override
    public void removeElemMapBoundary(IMapElement object){
        mapBoundary.removeObject(object);
    }
    @Override
    public void addElemMapBoundary(IMapElement object){
        mapBoundary.addObject(object);
    }
}
