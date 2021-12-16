package agh.ics.oop;

import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {
    private final SortedSet<IMapElement> xSet = new TreeSet<>(new ComparatorX());
    private final SortedSet<IMapElement> ySet = new TreeSet<>(new ComparatorY());


    public void addObject(IMapElement mapElement) {
        xSet.add(mapElement);
        ySet.add(mapElement);
    }

    public void removeObject(IMapElement mapElement) {
        xSet.remove(mapElement);
        ySet.remove(mapElement);
    }

    public Vector2d[] getSize() {
        return new Vector2d[]{new Vector2d(xSet.first().getPosition().x, ySet.first().getPosition().y), new Vector2d(xSet.last().getPosition().x, ySet.last().getPosition().y)};
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        ;
    }

    @Override
    public String toString() {
        System.out.println("size: " + ySet.size());
        for (IMapElement cur : ySet) {
            System.out.println(cur.getPosition() + " " + cur);
        }
        return "END\n";
    }
}
