package agh.ics.oop;

import agh.ics.oop.gui.App;
import java.util.*;
import static java.lang.System.out;

public class SimulationEngine implements Runnable{
    private final AbstractWorldMap map;
    private final LinkedList<IAnimalMoved> observersList = new LinkedList<>();
    private int moveDelay;
    final private int startEnergy;
    public SimulationEngine(AbstractWorldMap givenMap, Set<Vector2d> position, int givenRefreshRate, int givenStartEnergy,
                            App app, int givenMoveEnergy){
        this.startEnergy = givenStartEnergy;
        this.map = givenMap;
        this.moveDelay = givenRefreshRate;
        for (Vector2d vector2d : position) {
            Animal tmp = new Animal(this.map, vector2d,startEnergy,
                    new Random().ints(32,0,7).toArray(),givenMoveEnergy);
            tmp.setMaxEnergy(givenStartEnergy);
            this.map.place(tmp);
        }
        this.addObserver(app);
    }
    public void run(){
        while (true){
            this.map.removeDeadAnimals();
            //try {
            this.map.animalMovement();
            /*}catch (ConcurrentModificationException ex){
                StackTraceElement[] tmp = ex.getStackTrace();
                for(StackTraceElement cur : tmp){
                    out.println(cur);
                }out.println();
            }*/
            this.map.animalReproduction(this.startEnergy);
            this.map.addGrass();

            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                System.out.println("SLEEP INTERRUPTED");
            }
            updateGUI();
        }
    }

    public String toString(){
        return map.toString();
    }

    private void updateGUI(){
        for(IAnimalMoved observer : observersList){
            observer.update(this.map);
        }
    }
    public void addObserver(IAnimalMoved observer){
        observersList.add(observer);
    }
    public void removeObserver(IAnimalMoved observer){
        observersList.remove(observer);
    }
}
