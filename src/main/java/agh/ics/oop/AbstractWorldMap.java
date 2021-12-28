package agh.ics.oop;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    final protected Map<Vector2d, LinkedList<Animal>> animalMap = new ConcurrentHashMap<>();
    final protected Map<Vector2d, Grass> grassMap = new ConcurrentHashMap<>();
    protected final Vector2d[] mapSize = {new Vector2d(0, 0), new Vector2d(0, 0)};
    protected final Vector2d[] jungleSize;
    final int moveEnergy;
    final int plantEnergy;
    private int howMuchMagicHappened = 0;
    final private Map<String, Integer> dominantOfGenotype = new HashMap<>();
    public AbstractWorldMap(int width, int height, double jungleRatio, int givenEnergyMove, int givenPlantEnergy) {//generate grass on the filed
        this.mapSize[0] = new Vector2d(0, 0);
        this.mapSize[1] = new Vector2d(width - 1, height - 1);
        this.jungleSize = findJungleLocation(width, height, jungleRatio);
        this.moveEnergy = givenEnergyMove;
        this.plantEnergy = givenPlantEnergy;
    }

    private Vector2d[] findJungleLocation(int xMax, int yMax, double jungleRatio) {
        long mapArea = (long) xMax * (long)yMax;
        int jungleArea = (int)((jungleRatio * mapArea) /(1.0 + jungleRatio));
        int xJungle = 1, yJungle = 1;
        Vector2d[] expansionOrder = {new Vector2d(0, 1), new Vector2d(1, 0)};

        int expIndex = 0;
        while (xJungle * yJungle < jungleArea) {
            //out.println(xJungle + " " + yJungle);
            xJungle = Math.min(xJungle + expansionOrder[expIndex].x, xMax);
            yJungle = Math.min(yJungle + expansionOrder[expIndex].y, yMax);
            expIndex++;
            expIndex %= 2;
        }
        //out.println("raw params " + xJungle + " " + yJungle);
        int currentInaccuracy, smallerXInaccuracy, smallerYInaccuracy;
        currentInaccuracy = Math.abs(jungleArea - (xJungle * yJungle));
        smallerXInaccuracy = Math.abs(jungleArea - (Math.max(xJungle - 1,1) * yJungle));
        smallerYInaccuracy = Math.abs(jungleArea - (xJungle * Math.max(yJungle - 1,1)));
        if (currentInaccuracy > smallerXInaccuracy && smallerYInaccuracy >= smallerXInaccuracy) {
            xJungle--;
        } else if (currentInaccuracy > smallerYInaccuracy) {
            yJungle--;
        }
        Vector2d bottomLeftCorner = new Vector2d((xMax - xJungle + 1) / 2, (yMax - yJungle + 1) / 2);
        Vector2d upperRightCorner = new Vector2d((xMax - xJungle + 1) / 2 + xJungle - 1, (yMax - yJungle + 1) / 2 + yJungle - 1);
        return new Vector2d[]{bottomLeftCorner, upperRightCorner};
    }

    public abstract boolean canMoveTo(Vector2d position);

    public void place(Animal animal) {
        if (!animal.getPosition().follows(mapSize[0]) || !animal.getPosition().precedes(mapSize[1]))
            throw new IllegalArgumentException(animal.getPosition() + " is outside the map");
        if (!animalMap.containsKey(animal.getPosition())) {
            animalMap.put(animal.getPosition(), new LinkedList<>());
            animalMap.get(animal.getPosition()).add(animal);
            this.dominantUpdate(animal.getGenotype(), true);
            return;
        }
        throw new IllegalArgumentException(animal.getPosition() + " is already occupied");
    }

    public boolean isOccupied(Vector2d position) {
        return animalMap.containsKey(position) || grassMap.containsKey(position);
    }

    public Object objectAt(Vector2d position) {
        if (animalMap.containsKey(position)) {
            return getAnimalWithMaxEnergy(position);
        }
        if (grassMap.containsKey(position)) {
            return grassMap.get(position);
        }
        return null;
    }

    public String toString() {
        return new MapVisualizer(this).draw(mapSize[0], mapSize[1]);
    }

    public Vector2d[] getMapSize() {
        return mapSize;
    }

    public void animalMovement(){
        for (Vector2d position : animalMap.keySet()) {
            List<Animal> animalsAtPosition = new LinkedList<>(animalMap.get(position));
            for (Animal animal : animalsAtPosition) {
                animal.move(animal.getGenotype()[new Random().nextInt(32)]);
            }
        }
    }

    private Animal getAnimalWithMaxEnergy(Vector2d position){
        if(!animalMap.containsKey(position) || animalMap.get(position).size() == 0)
            throw new IllegalArgumentException("Position without animals was given");
        Animal animalFound = animalMap.get(position).get(0);
        for(Animal animal : animalMap.get(position)){
            if(animal.getEnergy() > animalFound.getEnergy()) {
                animalFound = animal;
            }
        }
        return animalFound;
    }

    public int[] removeDeadAnimals() {
        int howManyJustDied = 0, sumLifeLength = 0;
        for(Vector2d position : animalMap.keySet()){
            List<Animal> deadAnimals = new ArrayList<>();
            for(Animal animal : animalMap.get(position)){
                if(animal.getEnergy() <= 0){
                    deadAnimals.add(animal);
                    dominantUpdate(animal.getGenotype(),false);
                }
            }
            for(Animal animal : deadAnimals){
                sumLifeLength += animal.getLifeLength();
                animalMap.get(position).remove(animal);
            }
            howManyJustDied += deadAnimals.size();
        }
        animalMap.keySet().removeIf(position -> animalMap.get(position).isEmpty());
        return new int[]{howManyJustDied,sumLifeLength};
    }

    public HashMap<Vector2d, IMapElement> getObjects() {
        HashMap<Vector2d, IMapElement> objects = new HashMap<>(this.grassMap);
        animalMap.keySet().forEach(animalPosition -> objects.put(animalPosition, getAnimalWithMaxEnergy(animalPosition)));
        return objects;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) throws IllegalArgumentException {
        //out.println(oldPosition + " " + newPosition);
        if(animalMap.get(oldPosition) == null){
            throw new NullPointerException(oldPosition + "is non existing");
        }
        animalMap.get(oldPosition).remove(animal);
        //out.println(animalMap.get(oldPosition).get(0));
        if (animalMap.get(oldPosition).isEmpty()) {
            animalMap.remove(oldPosition);
        }
        if (!animalMap.containsKey(newPosition))
            animalMap.put(newPosition, new LinkedList<>());
        animalMap.get(newPosition).add(animal);
    }

    public void eatGrass() {
        for (Vector2d animalPosition : animalMap.keySet()) {
            if (grassMap.containsKey(animalPosition)) {
                grassMap.remove(animalPosition);

                int howManyWillEat = 0;
                Animal mostEnergeticAnimal = getAnimalWithMaxEnergy(animalPosition);
                for (Animal animal : animalMap.get(animalPosition)) {
                    if (mostEnergeticAnimal.getEnergy() == animal.getEnergy())
                        howManyWillEat++;
                    else
                        break;
                }
                for (Animal animal : animalMap.get(animalPosition)) {
                    if (mostEnergeticAnimal.getEnergy() == animal.getEnergy())
                        animal.energyGain(plantEnergy / howManyWillEat);
                    else
                        break;
                }
            }
        }
    }

    public void animalReproduction(int startEnergy) {
        for (Vector2d position : animalMap.keySet()) {
            if (animalMap.get(position).size() >= 2) {

                //Iterator<Animal> animalIterator = animalMap.get(position).iterator();
                Animal parent0 = getAnimalWithMaxEnergy(position);
                Animal parent1 = new Animal(this,new Vector2d(0,0),-1,
                        new Random().ints(32,0,7).toArray(), 0);
                for(Animal potentialParent : animalMap.get(position)){
                    if(potentialParent.getEnergy() > parent1.getEnergy() && potentialParent != parent0) {
                        parent1 = potentialParent;
                    }
                }
                parent0.incrementOffspringCounter();
                parent1.incrementOffspringCounter();
                if (parent0.getEnergy() >= startEnergy / 2.0 && parent1.getEnergy() >= startEnergy / 2.0) {
                    int[] parent0Genotype = parent0.getGenotype(), parent1Genotype = parent1.getGenotype();
                    int genotypeLength = parent0Genotype.length;
                    int[] childGenotype;
                    int numberOfParent0Genes = parent0.getEnergy() * genotypeLength /
                            (parent0.getEnergy() + parent1.getEnergy());
                    int parent0Side = new Random().nextInt(2);
                    if (parent0Side == 0) {
                        childGenotype = writeGenotype(parent0Genotype, parent1Genotype, numberOfParent0Genes);
                    } else {
                        childGenotype = writeGenotype(parent1Genotype, parent0Genotype, 32 - numberOfParent0Genes);
                    }
                    Animal child = new Animal(this, position, parent0.getEnergy() / 4 + parent1.getEnergy() / 4, childGenotype,moveEnergy);
                    parent0.energyLoss(parent0.getEnergy() / 4);
                    parent1.energyLoss(parent1.getEnergy() / 4);
                    animalMap.get(position).add(child);
                    dominantUpdate(childGenotype, true);
                }
            }
        }
    }

    private int[] writeGenotype(int[] leftGenotype, int[] rightGenotype, int quantityOfLeft) {
        int[] newGenotype = new int[leftGenotype.length];
        int newGenotypeInd = 0;
        for (int i = 0; i < quantityOfLeft; i++) {
            newGenotype[newGenotypeInd++] = leftGenotype[i];
        }
        for (int i = leftGenotype.length - 1; i >= quantityOfLeft; i--) {
            newGenotype[newGenotypeInd++] = rightGenotype[i];
        }
        return newGenotype;
    }

    public void addGrass() {
        Random generator = new Random();
        int jungleWidth = jungleSize[1].x - jungleSize[0].x + 1;
        int jungleHeight = jungleSize[1].y - jungleSize[0].y + 1;
        Vector2d newGrassPosition = new Vector2d(-1, -1);

        for (int i = 0; i < 100; i++) {
            newGrassPosition = new Vector2d(generator.nextInt(jungleWidth) + jungleSize[0].x, generator.nextInt(jungleHeight) + jungleSize[0].y);
            if (!grassMap.containsKey(newGrassPosition))
                break;
        }
        if (!grassMap.containsKey(newGrassPosition))
            grassMap.put(newGrassPosition, new Grass(newGrassPosition));
        else {
            newGrassPosition = bruteJungleGenerator();
            if (!newGrassPosition.equals(new Vector2d(-1, -1)))
                grassMap.put(newGrassPosition, new Grass(newGrassPosition));
        }

        newGrassPosition = new Vector2d(-1, -1);
        for (int i = 0; i < 100; i++) {
            newGrassPosition = new Vector2d(generator.nextInt(mapSize[1].x + 1), generator.nextInt(mapSize[1].y + 1));
            if (newGrassPosition.follows(jungleSize[0]) && newGrassPosition.precedes(jungleSize[1])) {
                continue;
            }
            if (!grassMap.containsKey(newGrassPosition))
                break;
        }
        if (!grassMap.containsKey(newGrassPosition) && !(newGrassPosition.follows(jungleSize[0]) && newGrassPosition.precedes(jungleSize[1]))) {
            grassMap.put(newGrassPosition, new Grass(newGrassPosition));
        } else {
            newGrassPosition = bruteSavannaGenerator();
            if (!newGrassPosition.equals(new Vector2d(-1, -1))) {
                grassMap.put(newGrassPosition, new Grass(newGrassPosition));
            }
        }
    }

    private Vector2d bruteJungleGenerator() {
        for (int i = jungleSize[0].x; i <= jungleSize[1].x; i++) {
            for (int j = jungleSize[0].y; j <= jungleSize[1].y; j++) {
                if (!grassMap.containsKey(new Vector2d(i, j))) {
                    return new Vector2d(i, j);
                }
            }
        }
        return new Vector2d(-1, -1);
    }

    private Vector2d bruteSavannaGenerator() {
        //out.println(mapSize[1].x + " " + mapSize[1].y);
        for (int i = 0; i <= mapSize[1].x; i++) {
            for (int j = 0; j <= mapSize[1].y; j++) {
                Vector2d tmpVector = new Vector2d(i, j);
                if (tmpVector.follows(jungleSize[0]) && tmpVector.precedes(jungleSize[1])) {
                    continue;
                }
                /*if(jungleSize[0].x <= i && i <= jungleSize[1].x && jungleSize[0].y <= j && j <= jungleSize[1].y) {
                        j += (jungleSize[1].y - jungleSize[0].y + 1);
                    }
                }*/
                if (!grassMap.containsKey(tmpVector)) {
                    return tmpVector;
                }
            }
        }
        return new Vector2d(-1, -1);
    }

    public Vector2d[] getJungleSize(){
        return jungleSize;
    }

    public int getAnimalQuantity() {
        int animalQuantity = 0;
        for(Vector2d position : animalMap.keySet()){
            animalQuantity += animalMap.get(position).size();
        }
        return animalQuantity;
    }

    public int getGrassQuantity() { return grassMap.size(); }

    boolean checkForMagicSituation(int startEnergy) {
        if(howMuchMagicHappened < 3 && this.getAnimalQuantity() == 5) {
            howMuchMagicHappened++;
            List<Vector2d> newPositions = new ArrayList<>();
            Vector2d newPosition;
            for(int i = 0; i < Math.min(5,(mapSize[1].x + 1) * (mapSize[1].y + 1) - 5); i++){
                do {
                    newPosition = new Vector2d(new Random().nextInt(mapSize[1].x), new Random().nextInt(mapSize[1].y));
                }while (animalMap.containsKey(newPosition) || newPositions.contains(newPosition));
                newPositions.add(newPosition);
            }
            List<Animal> newAnimals = new ArrayList<>();
            for(Vector2d position : animalMap.keySet()){
                for(int i = 0; i <animalMap.get(position).size(); i++){
                    newAnimals.add(new Animal(this,newPositions.get(i), startEnergy,
                            animalMap.get(position).get(i).getGenotype(), moveEnergy));
                }
            }
            for(Animal animal : newAnimals){
                this.place(animal);
            }
            return true;
        }
        else
            return false;

    }

    private void dominantUpdate(int[] genotype, boolean mode){
        StringBuilder genotypeWordBuilder = new StringBuilder();
        for(int gene : genotype){
            genotypeWordBuilder.append(gene);
        }
        String genotypeWord = genotypeWordBuilder.toString();
        if (mode){
            if(dominantOfGenotype.containsKey(genotypeWord))
                dominantOfGenotype.replace(genotypeWord,dominantOfGenotype.get(genotypeWord) + 1);
            else
                dominantOfGenotype.put(genotypeWord,1);
        } else {
            if(dominantOfGenotype.containsKey(genotypeWord)){
                if(dominantOfGenotype.get(genotypeWord) == 1)
                    dominantOfGenotype.remove(genotypeWord);
                else
                    dominantOfGenotype.replace(genotypeWord, dominantOfGenotype.get(genotypeWord) - 1);
            }
        }
    }
    public double getEnergySum(){
        long energySum = 0;
        for(Vector2d position : animalMap.keySet()){
            for(Animal animal : animalMap.get(position)){
                energySum += animal.getEnergy();
            }
        }
        return energySum;
    }

    public int getOffspringTotal(){
        int offspringTotal = 0;
        for(Vector2d position : animalMap.keySet()){
            for(Animal animal : animalMap.get(position)){
                offspringTotal += animal.getOffspringCounter();
            }
        }
        return offspringTotal;
    }
}

