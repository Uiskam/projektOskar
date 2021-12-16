package agh.ics.oop;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

class ComparatorX implements Comparator<IMapElement> {
    @Override
    public int compare(IMapElement o1, IMapElement o2) {
        if(o1.getPosition().x < o2.getPosition().x)
            return -1;
        if(o1.getPosition().x > o2.getPosition().x)
            return 1;
        if(o1.getPosition().y < o2.getPosition().y)
            return -1;
        if(o1.getPosition().y > o2.getPosition().y)
            return 1;
        if(o1 instanceof Animal && o2 instanceof Grass)
            return 1;
        if(o1 instanceof Grass && o2 instanceof Animal)
            return -1;
        return 0;
    }
}

class ComparatorY implements Comparator<IMapElement> {
    @Override
    public int compare(IMapElement o1, IMapElement o2) {
        if(o1.getPosition().y < o2.getPosition().y)
            return -1;
        if(o1.getPosition().y > o2.getPosition().y)
            return 1;
        if(o1.getPosition().x < o2.getPosition().x)
            return -1;
        if(o1.getPosition().x > o2.getPosition().x)
            return 1;
        if(o1 instanceof Animal && o2 instanceof Grass)
            return 1;
        if(o1 instanceof Grass && o2 instanceof Animal)
            return -1;
        return 0;
    }
}

