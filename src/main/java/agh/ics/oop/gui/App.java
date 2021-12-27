package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

import static java.lang.Math.abs;
import static java.lang.Math.decrementExact;
import static java.lang.System.*;

public class App extends Application implements IAnimalMoved {
    private final GridUpdate gridUpdater = new GridUpdate();
    private AbstractWorldMap map;
    private final GridPane gridPane = new GridPane();
    private Thread engineThread;
    private VBox menuWindow;
    ArrayList<HBox> menuParamEntrance = new ArrayList<>();
    ArrayList<Button> guiButtons = new ArrayList<>();


    @Override
    public void init() throws IllegalArgumentException {
        menuWindow = createMenu();

    }

    @Override
    public void start(Stage primaryStage) {
        Scene menuScene = new Scene(this.menuWindow, 600, 600);
        primaryStage.setScene(menuScene);
        primaryStage.show();

        guiButtons.get(GuiButtons.START.getIndex()).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int height, width, startEnergy, moveEnergy, plantEnergy, initialNumberOfAnimals;
                boolean magicModeWrapped, magicModeRect, inputDataError = false;
                double jungleRatio;
                /*do {
                    //TextField tmp = new TextField();
                    Node input = menuParamEntrance.get(MenuParamEntrance.HEIGHT.getIndex()).getChildren().get(1);
                    if(input instanceof TextField){
                        ((TextField) input).getText()
                    }
                    String tmp =
                }while (inputDataError);*/
                Label label  = new Label("DUPA");
                Scene simScene = new Scene(label,100,100);
                primaryStage.setScene(simScene);
            }
        });
    }


    @Override
    public void animalMoved() {
        gridUpdater.setParams(this.gridPane, this.map);
        Platform.runLater(gridUpdater::run);
    }

    private HBox createBoxWithLabelAndText(String labelText) {
        Label label = new Label(labelText);
        TextField textField = new TextField();
        HBox hBox = new HBox(label, textField);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    private VBox createMenu() {
        for(GuiButtons curButton : GuiButtons.values()){
            guiButtons.add(new Button(curButton.toString()));
        }
        for (MenuParamEntrance curVal : MenuParamEntrance.values()) {
            if (curVal != MenuParamEntrance.BORDERLESS_MAP_CHECK && curVal != MenuParamEntrance.ENCLOSED_MAP_CHECK) {
                menuParamEntrance.add(new HBox(new Label(curVal.toString()), new TextField()));
            } else {
                menuParamEntrance.add(new HBox(new Label(curVal.toString()), new CheckBox()));
            }
            menuParamEntrance.get(menuParamEntrance.size() - 1).setAlignment(Pos.CENTER_LEFT);

        }

        Label menuHeading = new Label("Set initial simulation conditions");
        VBox mapParamArea = new VBox(new Label("map parameters: "),
                menuParamEntrance.get(MenuParamEntrance.HEIGHT.getIndex()),
                menuParamEntrance.get(MenuParamEntrance.WIDTH.getIndex()),
                menuParamEntrance.get(MenuParamEntrance.JUNGLE_RATIO.getIndex()));
        VBox simParamArea = new VBox(new Label("simulation engine parameters: "),
                menuParamEntrance.get(MenuParamEntrance.START_ENERGY.getIndex()),
                menuParamEntrance.get(MenuParamEntrance.MOVE_ENERGY.getIndex()),
                menuParamEntrance.get(MenuParamEntrance.PLANT_ENERGY.getIndex()),
                menuParamEntrance.get(MenuParamEntrance.INITIAL_ANIMAL_NUMBER.getIndex()));
        HBox mainParamsArea = new HBox(mapParamArea, simParamArea);

        HBox setMagicArea = new HBox(new Label("mark to set magic mode for: "),
                menuParamEntrance.get(MenuParamEntrance.BORDERLESS_MAP_CHECK.getIndex()),
                menuParamEntrance.get(MenuParamEntrance.ENCLOSED_MAP_CHECK.getIndex()));

        VBox menuBox = new VBox(menuHeading,mainParamsArea,setMagicArea,guiButtons.get(GuiButtons.START.getIndex()));
        menuBox.setAlignment(Pos.TOP_CENTER);
        menuBox.setFillWidth(false);
        menuBox.setSpacing(5);
        return menuBox;
    }

}
