package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    static Image[] animalHealth;
    static Image animalSkin;
    static Image grassSkin;
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
            grassSkin = new Image(new FileInputStream("src/main/resources/grass.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    StackPane mapObject;
    public GuiElementBox(IMapElement element){
        ImageView energyLvl = null, texture;
        if(element instanceof Animal){
            int maxEnergy = ((Animal) element).getMaxEnergy();
            int currentEnergy = ((Animal) element).getEnergy();
            int index = (int)(Math.min(10 * Math.max(0.0,currentEnergy/(double)maxEnergy),9));
            energyLvl = new ImageView(animalHealth[index]);
            energyLvl.setFitWidth(20);
            energyLvl.setFitHeight(20);
            texture = new ImageView(animalSkin);
        }
        else{
            texture = new ImageView(grassSkin);
        }
        texture.setFitHeight(20);
        texture.setFitWidth(20);
        if(energyLvl != null){
            mapObject = new StackPane(energyLvl,texture);
        }
        else {
            mapObject = new StackPane(texture);
        }

        mapObject.setAlignment(Pos.CENTER);

    }

    public StackPane getVbox() {
        return mapObject;
    }
}
