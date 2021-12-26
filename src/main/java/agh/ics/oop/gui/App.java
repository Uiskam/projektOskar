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
    private final GridUpdate gridUpdater = new GridUpdate();
    private AbstractWorldMap map;
    private final GridPane gridPane = new GridPane();
    private Thread engineThread;
    private HBox hBox;
    private VBox controls;

    @Override
    public void start(Stage primaryStage){

        gridUpdater.update(this.gridPane, this.map);
        Scene scene = new Scene(this.hBox, 600, 600);

        gridPane.setGridLinesVisible(true);
        primaryStage.setScene(scene);
        primaryStage.show();
        //engineThread.start();
    }

    @Override
    public void init() throws IllegalArgumentException{
        this.map = new WrappedMap(10,10,0.5);
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        SimulationEngine engine = new SimulationEngine(map, positions,300,10,10,
                this);

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
                    //new GridUpdate(gridPane,map).run();
                    //createGrid();
                    gridUpdater.update(gridPane, map);
                    gridPane.setGridLinesVisible(true);
                    engineThread = new Thread(engine);
                    engineThread.start();

                }
            });
    }

    @Override
    public void animalMoved() {
        //out.println("sadsafdiuygsafuyeragfuyeraUYBIUYTIUTV");
        gridUpdater.setParams(this.gridPane,this.map);
        Platform.runLater(gridUpdater::run);
    }

}
