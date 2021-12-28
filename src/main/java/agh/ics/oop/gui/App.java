package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.*;

import static java.lang.System.*;

public class App extends Application implements IAnimalMoved {
    private final GridUpdate wrappedGridUpdater = new GridUpdate();
    private final GridUpdate rectangularGridUpdater = new GridUpdate();
    private final ChartUpdate wrappedChartUpdater = new ChartUpdate();
    private final ChartUpdate rectChartUpdater = new ChartUpdate();
    private WrappedMap wrappedMap;
    private RectangularMap rectangularMap;

    private final GridPane wrappedGridPane = new GridPane();
    private final GridPane rectangularGridPane = new GridPane();

    private VBox menuWindow;
    ArrayList<HBox> menuParamEntrance = new ArrayList<>();
    ArrayList<Button> guiButtons = new ArrayList<>();


    @Override
    public void init() throws IllegalArgumentException {
        menuWindow = createMenu();

    }

    @Override
    public void start(Stage primaryStage) {
        final NumberAxis wrappedXAxis = new NumberAxis();
        wrappedXAxis.setForceZeroInRange(false);
        final NumberAxis wrappedYAxis = new NumberAxis();
        final NumberAxis rectXAxis = new NumberAxis();
        rectXAxis.setForceZeroInRange(false);
        final NumberAxis rectYAxis = new NumberAxis();
        final LineChart<Number, Number> wrappedLineChart = new LineChart<Number, Number>(wrappedXAxis, wrappedYAxis);
        final LineChart<Number, Number> rectLineChart = new LineChart<Number, Number>(rectXAxis, rectYAxis);
        Scene menuScene = new Scene(this.menuWindow, 600, 225);
        primaryStage.setScene(menuScene);
        primaryStage.show();
        guiButtons.get(GuiButtons.START.getIndex()).setOnAction(event -> {
            int height, width, startEnergy, moveEnergy, plantEnergy, initialNumberOfAnimals, refreshRate;
            boolean magicModeWrapped, magicModeRect;
            double jungleRatio;
            Label errorText;
            String tmp;
            try {
                tmp = getData(MenuParamEntrance.HEIGHT);
                height = Integer.parseInt(tmp);
                tmp = getData(MenuParamEntrance.WIDTH);
                width = Integer.parseInt(tmp);
                tmp = getData(MenuParamEntrance.JUNGLE_RATIO);
                jungleRatio = Double.parseDouble(tmp);
                tmp = getData(MenuParamEntrance.START_ENERGY);
                startEnergy = Integer.parseInt(tmp);
                tmp = getData(MenuParamEntrance.MOVE_ENERGY);
                moveEnergy = Integer.parseInt(tmp);
                tmp = getData(MenuParamEntrance.PLANT_ENERGY);
                plantEnergy = Integer.parseInt(tmp);
                tmp = getData(MenuParamEntrance.INITIAL_ANIMAL_NUMBER);
                initialNumberOfAnimals = Integer.parseInt(tmp);
                tmp = getData(MenuParamEntrance.REFRESH_RATE);
                refreshRate = Integer.parseInt(tmp);
                magicModeWrapped = ((CheckBox) menuParamEntrance.get(MenuParamEntrance.BORDERLESS_MAP_CHECK.getIndex()).getChildren().get(1)).isSelected();
                magicModeRect = ((CheckBox) menuParamEntrance.get(MenuParamEntrance.ENCLOSED_MAP_CHECK.getIndex()).getChildren().get(1)).isSelected();

            } catch (NumberFormatException err) {
                out.println("HERE ");
                errorText = new Label("""
                        All values except for jungle ratio (which is type is double . as separator) MUST be positive integers
                        Tex fields CANNOT contain any character apart from digits, this includes white character
                        All fields MUST be filled with wanted valuesPlease restart program and input correct data
                        """);
                Scene errorScene = new Scene(errorText, 600, 100);
                primaryStage.setScene(errorScene);
                return;
            }
            Set<Vector2d> initialPositions = getStartingPositions(initialNumberOfAnimals, width, height);
            wrappedMap = new WrappedMap(width, height, jungleRatio, moveEnergy, plantEnergy);
            rectangularMap = new RectangularMap(width, height, jungleRatio, moveEnergy, plantEnergy);
            SimulationEngine wrappedSim = new SimulationEngine(wrappedMap, initialPositions, refreshRate, startEnergy,
                    this, moveEnergy);
            SimulationEngine rectangularSim = new SimulationEngine(rectangularMap, initialPositions, refreshRate, startEnergy,
                    this, moveEnergy);
            wrappedGridUpdater.update(wrappedGridPane, wrappedMap);
            rectangularGridUpdater.update(rectangularGridPane, rectangularMap);
            Label wrappedMapLabel = new Label("Boundaryless Map"), rectMapLabel = new Label("Restricted Map");
            wrappedMapLabel.setFont(new Font("Arial Black", 20));
            rectMapLabel.setFont(new Font("Arial Black", 20));
            VBox wrappedSimBox = new VBox(wrappedMapLabel, wrappedGridPane);
            VBox rectSimBox = new VBox(rectMapLabel, rectangularGridPane);
            HBox maps = new HBox(wrappedSimBox, rectSimBox);
            maps.setSpacing(140);
            HBox charts = new HBox(wrappedLineChart, rectLineChart);
            charts.setSpacing(140);
            VBox scene = new VBox(maps, charts);
            scene.setSpacing(50);
            Scene simScene = new Scene(scene, 1600, 1000);

            primaryStage.setScene(simScene);
            wrappedChartUpdater.setParams(wrappedLineChart, wrappedSim);
            rectChartUpdater.setParams(rectLineChart, rectangularSim);
            Thread wrappedSimThread = new Thread(wrappedSim);
            Thread rectangularSimThread = new Thread(rectangularSim);
            wrappedSimThread.start();
            rectangularSimThread.start();
        });
    }

    private Set<Vector2d> getStartingPositions(int n, int width, int height) {
        n = Math.min(n, width * height);
        Set<Vector2d> initialPositions = new HashSet<>();
        Random generator = new Random();
        for (int i = 0; i < n; i++) {
            Vector2d newPosition = new Vector2d(generator.nextInt(width), generator.nextInt(height));
            for (int j = 0; j < 100 && initialPositions.contains(newPosition); j++) {
                newPosition = new Vector2d(generator.nextInt(width), generator.nextInt(height));
            }
            if (initialPositions.contains(newPosition)) {
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        Vector2d curPos = new Vector2d(x, y);
                        if (!initialPositions.contains(curPos)) {
                            newPosition = curPos;
                            x = width;
                            y = height;
                        }
                    }
                }
            }
            initialPositions.add(newPosition);
        }
        return initialPositions;
    }

    @Override
    public void update(AbstractWorldMap map) {
        if (map instanceof WrappedMap) {
            wrappedGridUpdater.setParams(this.wrappedGridPane, this.wrappedMap);
            Platform.runLater(wrappedGridUpdater);
            Platform.runLater(wrappedChartUpdater);
        } else {
            rectangularGridUpdater.setParams(this.rectangularGridPane, this.rectangularMap);
            Platform.runLater(rectangularGridUpdater);
            Platform.runLater(rectChartUpdater);
        }
    }

    private VBox createMenu() {
        for (GuiButtons curButton : GuiButtons.values()) {
            guiButtons.add(new Button(curButton.toString()));
        }
        for (MenuParamEntrance curVal : MenuParamEntrance.values()) {
            if (curVal != MenuParamEntrance.BORDERLESS_MAP_CHECK && curVal != MenuParamEntrance.ENCLOSED_MAP_CHECK) {
                menuParamEntrance.add(new HBox(new Label(curVal.toString()), new TextField(curVal.defaultValue())));
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
                menuParamEntrance.get(MenuParamEntrance.INITIAL_ANIMAL_NUMBER.getIndex()),
                menuParamEntrance.get(MenuParamEntrance.REFRESH_RATE.getIndex()));
        HBox mainParamsArea = new HBox(mapParamArea, simParamArea);

        HBox setMagicArea = new HBox(new Label("mark to set magic mode for: "),
                menuParamEntrance.get(MenuParamEntrance.BORDERLESS_MAP_CHECK.getIndex()),
                menuParamEntrance.get(MenuParamEntrance.ENCLOSED_MAP_CHECK.getIndex()));

        VBox menuBox = new VBox(menuHeading, mainParamsArea, setMagicArea, guiButtons.get(GuiButtons.START.getIndex()));
        menuBox.setAlignment(Pos.TOP_CENTER);
        menuBox.setFillWidth(false);
        menuBox.setSpacing(5);
        return menuBox;
    }

    private String getData(MenuParamEntrance input) throws IllegalArgumentException {
        Node node = menuParamEntrance.get(input.getIndex()).getChildren().get(1);
        if (node instanceof TextField) {
            return ((TextField) node).getText();
        } else {
            throw new IllegalArgumentException("TEXT FIELD NOT FOUND");
        }
    }


}
