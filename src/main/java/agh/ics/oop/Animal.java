package agh.ics.oop;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Animal implements IMapElement{
    private Vector2d position;
    private MapDirection orientation;
    private final IWorldMap map;
    private final List<IPositionChangeObserver> observersList = new LinkedList<>();
    private final int[] genotype;
    private int energyLevel;

    public Animal(IWorldMap map, Vector2d initialPosition, int startEnergy, int[] givenGenotype){
        this.map = map;
        addObserver((IPositionChangeObserver) this.map);

        this.position = initialPosition;
        this.energyLevel = startEnergy;
        this.genotype = givenGenotype;//new Random().ints(32,0,7).toArray();
        Arrays.sort(this.genotype);
        this.orientation = MapDirection.values()[new Random().nextInt(MapDirection.values().length)];
    }

    public void move(int directionChange) {
        //int directionChange = genotype[new Random().nextInt(32)];
        switch (directionChange) {
            case 0 -> {
                Vector2d nextPosition = new Vector2d(this.position.x, this.position.y).add(this.orientation.toUnitVector());
                if(map.canMoveTo(nextPosition)){
                    if(map instanceof WrappedMap)
                        nextPosition = ((WrappedMap) map).wrapVector(nextPosition);
                    positionChanged(this.position,nextPosition,this);
                    this.position = nextPosition;//toIniteVector nie bierze pod uwagę zawijania koordynantó
                    //przez co pozycja zwierzęcie była ustawiona poza mpaą
                }
            }
            case 4 -> {
                Vector2d nextPosition = new Vector2d(this.position.x, this.position.y).subtract(this.orientation.toUnitVector());
                if(map.canMoveTo(nextPosition)){
                    if(map instanceof WrappedMap)
                        nextPosition = ((WrappedMap) map).wrapVector(nextPosition);
                    positionChanged(this.position,nextPosition,this);
                    this.position = nextPosition;
                }
            }
            default -> {
                IntStream.range(0,directionChange).forEach(i -> this.orientation = this.orientation.next());
            }
        }
    }

    public boolean isAt(Vector2d pos) {
        return this.position.equals(pos);
    }

    public void addObserver(IPositionChangeObserver observer){
        observersList.add(observer);
    }
    public void removeObserver(IPositionChangeObserver observer){
        observersList.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition,IMapElement object){
        for(IPositionChangeObserver observer : observersList){
            observer.positionChanged(oldPosition,newPosition,this);
        }
    }

    public String textureLocation(){
        return switch (this.orientation){
            case SOUTH ->"src/main/resources/up.png";
            case NORTH ->"src/main/resources/down.png";
            case EAST -> "src/main/resources/right.png";
            case WEST -> "src/main/resources/left.png";
            default -> "src/main/resources/left.png";

        };
    }

    @Override
    public String toString() {
        return this.orientation.toString();
    }

    public Vector2d getPosition(){
        return new Vector2d(this.position.x,this.position.y);
    }

    public int getEnergy() { return this.energyLevel; }

    public void energyGain(int energy) {energyLevel += energy;}

    public void energyLoss(int energy) {energyLevel -= energy;}

    public int[] getGenotype(){
        return this.genotype;
    }
}
