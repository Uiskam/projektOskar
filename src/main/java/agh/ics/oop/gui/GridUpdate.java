package agh.ics.oop.gui;

import agh.ics.oop.AbstractWorldMap;
import agh.ics.oop.IMapElement;
import agh.ics.oop.Vector2d;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.HashMap;

import static java.lang.Math.abs;

public class GridUpdate implements Runnable{
    private GridPane gridPane;
    private AbstractWorldMap map;
    public GridUpdate(){}

    public void update(GridPane givenGridPane, AbstractWorldMap givenMap){
        this.setParams(givenGridPane,givenMap);
        this.run();
    }
    public void setParams(GridPane givenGridPane, AbstractWorldMap givenMap){
        this.gridPane = givenGridPane;
        this.map = givenMap;
    }
    @Override
    public void run(){
        gridPane.setGridLinesVisible(false);
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getChildren().clear();
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);

        RowConstraints height = new RowConstraints(40);
        ColumnConstraints width = new ColumnConstraints(40);
        Vector2d[] mapSize = this.map.getMapSize();
        for(int i = 0; i <= abs(mapSize[1].y - mapSize[0].y) + 1; i++){
            this.gridPane.getRowConstraints().add(height);
        }
        for(int i = 0; i <= abs(mapSize[1].x - mapSize[0].x) + 1; i++){
            this.gridPane.getColumnConstraints().add(width);
        }
        for(int i = mapSize[0].x; i <= mapSize[1].x; i++){
            Label label = new Label(String.valueOf(i));
            gridPane.add(label,i - mapSize[0].x + 1,0);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        for(int i = mapSize[0].y; i <= mapSize[1].y; i++){
            Label label = new Label(String.valueOf(i));
            gridPane.add(label,0,i - mapSize[0].y + 1);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        Label label1 = new Label("y/x");
        gridPane.add(label1,0,0);
        GridPane.setHalignment(label1, HPos.CENTER);

        HashMap<Vector2d, IMapElement> mapObjects = this.map.getObjects();
        for(Vector2d cur : mapObjects.keySet()){
            Label label = new Label(mapObjects.get(cur).toString());
            GuiElementBox animalVisualisation = new GuiElementBox(mapObjects.get(cur));
            gridPane.add(animalVisualisation.getVbox(),mapObjects.get(cur).getPosition().x - mapSize[0].x + 1, mapObjects.get(cur).getPosition().y - mapSize[0].y + 1);
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }
}
