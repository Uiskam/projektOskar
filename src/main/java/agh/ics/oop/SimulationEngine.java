package agh.ics.oop;

import agh.ics.oop.gui.App;
import java.util.*;
import static java.lang.System.out;

public class SimulationEngine implements Runnable{
    private final AbstractWorldMap map;
    private final LinkedList<IAnimalMoved> observersList = new LinkedList<>();
    private int moveDelay;
    final private int startEnergy;
    List<Integer> animalQuantity = new ArrayList<>();
    List<Integer> grassQuantity= new ArrayList<>();
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
        if(this.map.checkForMagicSituation(this.startEnergy)){
            ;
        }
    }
    public void run(){
        for (int era = 0; ; era++){
            this.map.removeDeadAnimals();
            this.map.animalMovement();
            this.map.animalReproduction(this.startEnergy);
            this.map.addGrass();
            animalQuantity.add(this.map.getAnimalQuantity());
            animalQuantity.add(this.map.getGrassQuantity());
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
