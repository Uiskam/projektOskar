package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    private VBox menuWindow;



    @Override
    public void init() throws IllegalArgumentException{
        menuWindow = createMenu();
    }

    @Override
    public void start(Stage primaryStage){





        //gridUpdater.update(this.gridPane, this.map);
        //Scene scene = new Scene(this.hBox, 600, 600);
        Scene scene = new Scene(this.menuWindow, 600, 600);

        gridPane.setGridLinesVisible(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    @Override
    public void animalMoved() {
        gridUpdater.setParams(this.gridPane,this.map);
        Platform.runLater(gridUpdater::run);
    }

    private HBox createBoxWithLabelAndText(String labelText){
        Label label = new Label(labelText);
        TextField textField = new TextField();
        HBox hBox = new HBox(label,textField);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }
    private VBox createMenu(){
        VBox menu;
        Label menuHeading = new Label("Set initial simulation conditions");
        Label disclaimer = new Label("all values MUST be positive");

        Label mapParamHeading = new Label("map parameters:");
        HBox height = createBoxWithLabelAndText("height :");
        HBox width = createBoxWithLabelAndText("width :");
        HBox jungleRatio = createBoxWithLabelAndText("jungleRatio :");
        VBox mapParamArea = new VBox(mapParamHeading,height,width,jungleRatio);
        mapParamArea.setAlignment(Pos.TOP_CENTER);
        Label simParamHeading = new Label("simulation parameters:");
        HBox startEnergy = createBoxWithLabelAndText("start energy: ");
        HBox moveEnergy = createBoxWithLabelAndText("move energy: ");
        HBox plantEnergy = createBoxWithLabelAndText("plant energy: ");
        VBox simParamArea = new VBox(simParamHeading,startEnergy,moveEnergy,plantEnergy);

        HBox parametersSettingArea = new HBox(mapParamArea,simParamArea);

        Label wrappedMapLabel = new Label("Borderless map");
        CheckBox wrappedMapConsent = new CheckBox();

        VBox menuBox = new VBox(menuHeading,disclaimer,parametersSettingArea);
        menuBox.setAlignment(Pos.TOP_CENTER);
        menuBox.setFillWidth(false);
        return menuBox;

    }

}
