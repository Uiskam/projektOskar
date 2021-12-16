package agh.ics.oop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SimulationEngine implements IEngine, Runnable{
    private MoveDirection[] directions = {MoveDirection.FORWARD};
    private final List<Animal> animals = new ArrayList<>();
    private final IWorldMap map;
    private final LinkedList<IAnimalMoved> observersList = new LinkedList<>();
    private int moveDelay = 0;
    public SimulationEngine(MoveDirection[] moves, IWorldMap maps, Vector2d[] position,int stopLength){
        this.directions = moves;
        this.map = maps;
        this.moveDelay = stopLength;
        for (Vector2d vector2d : position) {
            Animal tmp = new Animal(maps, vector2d);
            maps.place(tmp);
            animals.add(tmp);
        }
    }
    public void setMoves(MoveDirection[] moves){
        this.directions = moves;
    }

    public void run(){
        for(int i = 0; i < this.directions.length; i++){
            animals.get(i % animals.size()).move(directions[i]);
            sthMoved();
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                //e.printStackTrace();
                System.out.println("SLEEP INTERUPTED");
            }

        }


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
