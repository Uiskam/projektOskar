package agh.ics.oop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.lang.System.out;

public class SimulationEngine implements IEngine, Runnable{
    private MoveDirection[] directions = {MoveDirection.FORWARD};
    private final List<Animal> animals = new ArrayList<>();
    private final IWorldMap map;
    private final LinkedList<IAnimalMoved> observersList = new LinkedList<>();
    private int moveDelay = 0;
    public SimulationEngine(IWorldMap givenMap, Vector2d[] position,int stopLength, int startEnergy, int moveEnergyValue){
        this.map = givenMap;
        this.moveDelay = stopLength;
        for (Vector2d vector2d : position) {
            Animal tmp = new Animal(this.map, vector2d,startEnergy, new Random().ints(32,0,7).toArray());
            this.map.place(tmp);
            animals.add(tmp);
        }
    }
    public void setMoves(MoveDirection[] moves){
        this.directions = moves;
    }

    public void run(){
        for(int i = 0; i < this.directions.length; i++){
            animals.get(i % animals.size()).move();
            sthMoved();
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                //e.printStackTrace();
                System.out.println("SLEEP INTERUPTED");
            }

        }
        map.removeDeadAnimals();



    }



    public String toString(){
        return map.toString();
    }

    private void sthMoved(){
        for(IAnimalMoved observer : observersList){
            observer.animalMoved();
        }
    }
    public void addObserver(IAnimalMoved observer){
        observersList.add(observer);
    }
    public void removeObserver(IAnimalMoved observer){
        observersList.remove(observer);
    }
}
