package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.IMapElement;
import agh.ics.oop.Vector2d;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    static Image[] animalHealth;
    static Image animalSkin, grassSkin, grassSavanna, grassJungle;

    static {
        try {
            animalHealth = new Image[]{
                    new Image(new FileInputStream("src/main/resources/health0.png")),
                    new Image(new FileInputStream("src/main/resources/health1.png")),
                    new Image(new FileInputStream("src/main/resources/health2.png")),
                    new Image(new FileInputStream("src/main/resources/health3.png")),
                    new Image(new FileInputStream("src/main/resources/health4.png")),
                    new Image(new FileInputStream("src/main/resources/health5.png")),
                    new Image(new FileInputStream("src/main/resources/health6.png")),
                    new Image(new FileInputStream("src/main/resources/health7.png")),
                    new Image(new FileInputStream("src/main/resources/health8.png")),
                    new Image(new FileInputStream("src/main/resources/health9.png")),
            };
            animalSkin = new Image(new FileInputStream("src/main/resources/giraffe.png"));
            grassSavanna = new Image(new FileInputStream("src/main/resources/grass.png"));
            grassJungle = new Image(new FileInputStream("src/main/resources/jungleGrass.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    StackPane mapObject;

    public GuiElementBox(IMapElement element, Vector2d[] jungleSize, int sideLength) {
        ImageView energyLvl = null, texture;
        if (element instanceof Animal) {
            int maxEnergy = ((Animal) element).getMaxEnergy();
            int currentEnergy = ((Animal) element).getEnergy();
            int index = (int) (Math.min(10 * Math.max(0.0, currentEnergy / (double) maxEnergy), 9));
            energyLvl = new ImageView(animalHealth[index]);
            energyLvl.setFitWidth(sideLength);
            energyLvl.setFitHeight(sideLength);
            texture = new ImageView(animalSkin);
        } else {
            texture = new ImageView(grassSkin);
        }
        texture.setFitHeight(sideLength);
        texture.setFitWidth(sideLength);
        ImageView jungle = new ImageView(grassJungle), savanna = new ImageView(grassSavanna);
        jungle.setFitHeight(sideLength);
        jungle.setFitWidth(sideLength);
        savanna.setFitHeight(sideLength);
        savanna.setFitWidth(sideLength);
        if (energyLvl != null) {
            mapObject = new StackPane(energyLvl, texture);
        } else {
            if (element.getPosition().follows(jungleSize[0]) && element.getPosition().precedes(jungleSize[1])) {
                mapObject = new StackPane(jungle, texture);
            } else {
                mapObject = new StackPane(savanna, texture);
            }
        }

        mapObject.setAlignment(Pos.CENTER);
    }

    public StackPane getStackPane() {
        return mapObject;
    }
}
