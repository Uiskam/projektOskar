package agh.ics.oop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
            Animal tmp = new Animal(this.map, vector2d,startEnergy,moveEnergyValue);
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

    private Vector2d[] findJungleLocation(int xMax, int yMax, int jungleRatio, int savannaRatio){
        int mapArea = xMax * yMax;
        int jungleArea = jungleRatio * mapArea / (jungleRatio + savannaRatio), xJungle = 1, yJungle = 1;
        Vector2d[] expansionOrder = {new Vector2d(0, 1), new Vector2d(1, 0)};

        int expIndex = 0;
        while (xJungle * yJungle < jungleArea) {
            xJungle = Math.min(xJungle + expansionOrder[expIndex].x, xMax);
            yJungle = Math.min(xJungle + expansionOrder[expIndex].y, yMax);
            expIndex++;
            expIndex %= 2;
        }
        int currentInaccuracy, smallerXInaccuracy, smallerYInaccuracy;
        currentInaccuracy = Math.abs(jungleArea - (xJungle * yJungle));
        smallerXInaccuracy = Math.abs(jungleArea - ((xJungle - 1) * yJungle));
        smallerYInaccuracy = Math.abs(jungleArea - (xJungle * (yJungle - 1)));
        out.println(currentInaccuracy + " " + smallerXInaccuracy + " " + smallerYInaccuracy);
        if(currentInaccuracy > smallerXInaccuracy && smallerYInaccuracy >= smallerXInaccuracy){
            xJungle--;
        }
        else if(currentInaccuracy > smallerYInaccuracy){
            yJungle--;
        }

        Vector2d bottomLeftCorner = new Vector2d((xMax - xJungle + 1) / 2, (yMax - yJungle + 1) / 2);
        Vector2d upperRightCorner = new Vector2d((xMax - xJungle + 1) / 2 + xJungle -1 , (yMax - yJungle + 1) / 2 + yJungle -1 );
        return new Vector2d[]{bottomLeftCorner, upperRightCorner};
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
