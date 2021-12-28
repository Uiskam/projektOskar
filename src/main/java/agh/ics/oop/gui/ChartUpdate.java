package agh.ics.oop.gui;

import agh.ics.oop.AbstractWorldMap;
import agh.ics.oop.SimulationEngine;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;

import java.util.Collections;
import java.util.List;
import java.util.SimpleTimeZone;

public class ChartUpdate implements Runnable{
    private LineChart lineChart;
    private AbstractWorldMap map;
    private SimulationEngine engine;
    List<Integer> animalQuantity;
    List<Integer> grassQuantity;
    List<Double> avgEnergyLvl;
    List<Double> avgLifeSpan;
    List<Double> avgOffspringCount;

    public ChartUpdate(){}

    public void setParams(LineChart givenLineCHart, SimulationEngine givenEngine){
        this.lineChart = givenLineCHart;
        this.engine = givenEngine;
    }

    @Override
    public void run(){
        animalQuantity = engine.getAnimalQuantity();
        grassQuantity = engine.getGrassQuantity();
        avgEnergyLvl = engine.getAvgEnergyLvl();
        avgLifeSpan = engine.getAvgLifeLength();
        avgOffspringCount = engine.getAvgOffspringQuantity();

        lineChart.getData().removeAll();
        lineChart.setAnimated(false);
        lineChart.setMaxHeight(200);
        lineChart.setMaxWidth(900);
        lineChart.setPrefWidth(800);
        lineChart.setCreateSymbols(false);
        XYChart.Series animaNumber = new XYChart.Series();
        XYChart.Series grassNumber = new XYChart.Series();
        XYChart.Series avgEnergy = new XYChart.Series();
        XYChart.Series avgLife = new XYChart.Series();
        XYChart.Series avgOffspring = new XYChart.Series();
        animaNumber.getData().removeAll(Collections.singleton(lineChart.getData().setAll()));
        animaNumber.setName("animal number");
        grassNumber.setName("grass number");
        avgEnergy.setName("average energy ");
        avgLife.setName("life expectancy");
        avgOffspring.setName("average kids quantity");
        for(int i = Math.max(0,animalQuantity.size()-100); i < animalQuantity.size(); i++){
            animaNumber.getData().add(new XYChart.Data(i,animalQuantity.get(i)));
            grassNumber.getData().add(new XYChart.Data(i,grassQuantity.get(i)));
            avgEnergy.getData().add(new XYChart.Data(i,avgEnergyLvl.get(i)));
            avgLife.getData().add(new XYChart.Data(i,avgLifeSpan.get(i)));
            avgOffspring.getData().add(new XYChart.Data(i,avgOffspringCount.get(i)));
        }
        lineChart.getData().addAll(animaNumber,grassNumber,avgEnergy,avgLife,avgOffspring);
    }
}
