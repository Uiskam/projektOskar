package agh.ics.oop;

import agh.ics.oop.gui.App;
import java.util.*;
import static java.lang.System.out;

public class SimulationEngine implements Runnable{
    private final AbstractWorldMap map;
    private final LinkedList<IAnimalMoved> observersList = new LinkedList<>();
    private final int moveDelay;
    final private int startEnergy;
    List<Integer> animalQuantity = new ArrayList<>();
    List<Integer> grassQuantity = new ArrayList<>();
    List<Double> avgEnergyLvl = new ArrayList<>();
    List<Double> avgLifeLength = new ArrayList<>();
    List<Double> avgOffspringQuantity = new ArrayList<>();
    private double lifeExpectancy = 0;
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
            int[] tmp = map.removeDeadAnimals();
            if(tmp[1] != 0){
                lifeExpectancy = (lifeExpectancy + tmp[1]/(double)tmp[0]) / 2.0;
            }
            map.animalMovement();
            map.animalReproduction(startEnergy);
            map.eatGrass();
            map.addGrass();
            animalQuantity.add(map.getAnimalQuantity());
            grassQuantity.add(map.getGrassQuantity());
            avgEnergyLvl.add(map.getEnergySum()/(double) animalQuantity.get(animalQuantity.size()-1));
            avgLifeLength.add(lifeExpectancy);
            avgOffspringQuantity.add(map.getOffspringTotal()/(double)map.getAnimalQuantity());
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

    public List<Integer> getAnimalQuantity(){
        return animalQuantity;
    }
    public List<Integer> getGrassQuantity(){
        return grassQuantity;
    }
    public List<Double> getAvgEnergyLvl(){
        return avgEnergyLvl;
    }
    public List<Double> getAvgLifeLength(){
        return avgLifeLength;
    }
    public List<Double> getAvgOffspringQuantity(){
        return avgOffspringQuantity;
    }

}
