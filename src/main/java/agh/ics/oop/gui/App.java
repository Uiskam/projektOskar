package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashMap;

import static java.lang.Math.abs;
import static java.lang.Math.decrementExact;
import static java.lang.System.*;
public class App extends Application implements IAnimalMoved{
    private AbstractWorldMap map;
    private final GridPane gridPane = new GridPane();
    private Thread engineThread;
    private HBox hBox;
    private VBox controls;

    @Override
    public void start(Stage primaryStage){

        //primaryStage.show();
        createGrid();
        Scene scene = new Scene(this.hBox, 800, 800);

        gridPane.setGridLinesVisible(true);
        primaryStage.setScene(scene);
        primaryStage.show();
        //engineThread.start();
    }

    @Override
    public void init() throws IllegalArgumentException{
        //String[] test_sq  = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        //String[] test_sq = getParameters().getRaw().toArray(new String[0]);
        //MoveDirection[] directions = OptionsParser.parse(test_sq);

        this.map = new WrappedMap(10,10);
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        SimulationEngine engine = new SimulationEngine(map, positions,300,10,10);
        engine.addObserver(this);

        Button startButton = new Button("START");
        TextField inputFiled = new TextField();
        controls = new VBox(startButton,inputFiled);
        hBox = new HBox(controls,gridPane);

            startButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    String tmp = inputFiled.getText();
                    String[] moves = tmp.split(" +");
                    try {
                        MoveDirection[] directions = OptionsParser.parse(moves);
                        engine.setMoves(directions);
                    }catch (IllegalArgumentException ex){
                        out.println(ex.getMessage());
                    }
                    createGrid();
                    gridPane.setGridLinesVisible(true);
                    engineThread = new Thread(engine);
                    engineThread.start();

                }
            });


    }




    @Override
    public void animalMoved() {
        //out.println("sadsafdiuygsafuyeragfuyeraUYBIUYTIUTV");
        Platform.runLater(this::createGrid);
    }

    private void createGrid(){
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

        HashMap<Vector2d,IMapElement> mapObjects = this.map.getObjects();
        for(Vector2d cur : mapObjects.keySet()){
            Label label = new Label(mapObjects.get(cur).toString());
            GuiElementBox animalVisualisation = new GuiElementBox(this.gridPane,mapObjects.get(cur));
            gridPane.add(animalVisualisation.getVbox(),mapObjects.get(cur).getPosition().x - mapSize[0].x + 1, mapObjects.get(cur).getPosition().y - mapSize[0].y + 1);
            GridPane.setHalignment(label, HPos.CENTER);
        }


    }
}
