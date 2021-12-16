package agh.ics.oop;

public class Grass implements IMapElement{
    private final Vector2d position;
    public Grass(Vector2d givenPosition){
        this.position = givenPosition;
    }

    public String toString(){ return "*";}

    public Vector2d getPosition() {
        return new Vector2d(position.x, position.y);
    }

    public String textureLocation(){
        return "src/main/resources/grass.png";
    }
}
