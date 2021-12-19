package agh.ics.oop;

import java.util.HashMap;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo
 *
 */
public interface IWorldMap{
    /**
     * Indicate if any object can move to the given position.
     *
     * @param position
     *            The position checked for the movement possibility.
     * @return True if the object can move to that position.
     */
    boolean canMoveTo(Vector2d position);

    /**
     * Place a animal on the map.
     *
     * @param animal
     *            The animal to place on the map.
     *
     */
    void  place(Animal animal);

    /**
     *
     * @param child
     *      newborn to be placed
     *
     */
    void placeChild(Animal child);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position
     *            Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an object at a given position.
     *
     * @param position
     *            The position of the object.
     * @return Object or null if the position is not occupied.
     */
    Object objectAt(Vector2d position);

    /**
     *
     * @return map size as array of Vector2d = {leftBottomCorner, upperRightCorner};
     */
    Vector2d[] getMapSize();

    /**
     * Removes all dead animals from the map
     */
    void removeDeadAnimals();

    /**
     *
     * @return Returns HashMap with all objects present on the map.
     */
    HashMap<Vector2d, IMapElement> getObjects();
}