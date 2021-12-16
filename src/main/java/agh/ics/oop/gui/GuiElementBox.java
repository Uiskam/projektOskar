package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.IMapElement;
import agh.ics.oop.Vector2d;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;


import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    Image image;
    ImageView imageView;
    VBox vbox = new VBox();
    Label label;

    public GuiElementBox(GridPane gridPane, IMapElement element){
        try {
            image = new Image(new FileInputStream(element.textureLocation()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if(element instanceof Animal){
                System.out.println("ERROR " + element.toString());
            }
            else{
                System.out.println("ERROR1 ");
            }
            //System.out.println();
        }
        imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        vbox.getChildren().add(imageView);
        vbox.setAlignment(Pos.CENTER);
        if(element instanceof Animal) {
            label = new Label("Z " + element.getPosition().toString());
        }
        else {
            label = new Label("Grass");
        }
        vbox.getChildren().add(label);
    }

    public VBox getVbox() {
        return vbox;
    }
}
