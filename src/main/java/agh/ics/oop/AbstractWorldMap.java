package agh.ics.oop;

import java.util.*;

import static java.lang.System.out;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    final protected Map<Vector2d, SortedSet<Animal>> animalMap = new LinkedHashMap<>();
    final protected Map<Vector2d, Grass> grassMap = new LinkedHashMap<>();
    protected final Vector2d[] mapSize = {new Vector2d(0, 0), new Vector2d(0, 0)};
    protected final Vector2d[] jungleSize;

    public AbstractWorldMap(int width, int height, int jungleRatio, int savannaRatio) {//generate grass on the filed
        mapSize[0] = new Vector2d(0, 0);
        mapSize[1] = new Vector2d(width - 1, height - 1);
        jungleSize = findJungleLocation(width, height, jungleRatio, savannaRatio);
    }

    private Vector2d[] findJungleLocation(int xMax, int yMax, int jungleRatio, int savannaRatio) {
        int mapArea = xMax * yMax;
        int jungleArea = jungleRatio * mapArea / (jungleRatio + savannaRatio), xJungle = 1, yJungle = 1;
        Vector2d[] expansionOrder = {new Vector2d(0, 1), new Vector2d(1, 0)};

        int expIndex = 0;
        while (xJungle * yJungle < jungleArea) {
            //out.println(xJungle + " " + yJungle);
            xJungle = Math.min(xJungle + expansionOrder[expIndex].x, xMax);
            yJungle = Math.min(yJungle + expansionOrder[expIndex].y, yMax);
            expIndex++;
            expIndex %= 2;
        }
        int currentInaccuracy, smallerXInaccuracy, smallerYInaccuracy;
        currentInaccuracy = Math.abs(jungleArea - (xJungle * yJungle));
        smallerXInaccuracy = Math.abs(jungleArea - ((xJungle - 1) * yJungle));
        smallerYInaccuracy = Math.abs(jungleArea - (xJungle * (yJungle - 1)));
        //out.println(currentInaccuracy + " " + smallerXInaccuracy + " " + smallerYInaccuracy);
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
            animalMap.put(animal.getPosition(), new TreeSet<>(new EnergyComparator()));
            animalMap.get(animal.getPosition()).add(animal);
            return;
        }
        throw new IllegalArgumentException(animal.getPosition() + " is already occupied");
    }

    public boolean isOccupied(Vector2d position) {
        return animalMap.containsKey(position) || grassMap.containsKey(position);
    }

    public Object objectAt(Vector2d position) {
        if (animalMap.containsKey(position)) {
            return animalMap.get(position).first();
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


    public void removeDeadAnimals() {
        animalMap.keySet().forEach(position ->
                animalMap.get(position).removeIf(animal -> animal.getEnergy() <= 0));
        animalMap.keySet().removeIf(position -> animalMap.get(position).isEmpty());
    }

    public HashMap<Vector2d, IMapElement> getObjects() {
        HashMap<Vector2d, IMapElement> objects = new HashMap<>(this.grassMap);
        animalMap.values().forEach(animalSet -> objects.put(animalSet.first().getPosition(), animalSet.first()));
        return objects;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        animalMap.get(oldPosition).remove(animal);
        if (animalMap.get(oldPosition).isEmpty())
            animalMap.remove(oldPosition);
        if (!animalMap.containsKey(newPosition))
            animalMap.put(newPosition, new TreeSet<>(new EnergyComparator()));
        animalMap.get(newPosition).add(animal);
    }

    public void removeEatenGrass(int plantEnergy) {
        for (Vector2d animalPosition : animalMap.keySet()) {
            if (grassMap.containsKey(animalPosition)) {
                grassMap.remove(animalPosition);

                int howManyWillEat = 0;
                SortedSet<Animal> animals = animalMap.get(animalPosition);
                for (Animal animal : animals) {
                    if (animals.first().getEnergy() == animal.getEnergy())
                        howManyWillEat++;
                    else
                        break;
                }
                for (Animal animal : animals) {
                    if (animals.first().getEnergy() == animal.getEnergy())
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
                Iterator<Animal> animalIterator = animalMap.get(position).iterator();
                Animal parent0 = animalIterator.next(), parent1 = animalIterator.next();
                if (parent0.getEnergy() >= startEnergy / 2.0 && parent1.getEnergy() >= startEnergy / 2.0) {
                    int[] parent0Genotype = parent0.getGenotype(), parent1Genotype = parent1.getGenotype();
                    int genotypeLength = parent0Genotype.length;
                    int[] childGenotype;
                    int numberOfParent0Genes = parent0.getEnergy() / (parent0.getEnergy() + parent1.getEnergy()) * genotypeLength;
                    int parent0Side = new Random().nextInt(2);
                    if (parent0Side == 0) {
                        childGenotype = writeGenotype(parent0Genotype, parent1Genotype, numberOfParent0Genes);
                    } else {
                        childGenotype = writeGenotype(parent1Genotype, parent0Genotype, 32 - numberOfParent0Genes);
                    }
                    Animal child = new Animal(this, position, parent0.getEnergy() / 4 + parent1.getEnergy() / 4, childGenotype);
                    parent0.energyLoss(parent0.getEnergy() / 4);
                    parent1.energyLoss(parent1.getEnergy() / 4);
                    animalMap.get(position).add(child);
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
}
