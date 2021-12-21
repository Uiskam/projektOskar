package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.logging.Handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GrassGenerationTest {
    @Test
    public void grassGenTest0(){
        int width = 100, height = 100, jungleRatio = 1, savannaRatio = 2;
        RectangularMap map = new RectangularMap(width,height,jungleRatio,savannaRatio);
        Vector2d[] jungleCords = map.jungleSize;
        int jungleArea = (jungleCords[1].x - jungleCords[0].x + 1) * (jungleCords[1].y - jungleCords[0].y + 1);
        HashMap<Vector2d,IMapElement> grassMap;
        int savannaGrassCounter, jungleGrassCounter;
        for(int i = 0; i < width*height; i++){
            savannaGrassCounter = 0; jungleGrassCounter = 0;
            map.addGrass();
            grassMap = map.getObjects();
            for(Vector2d grassPosition : grassMap.keySet()){
                if(grassPosition.follows(jungleCords[0]) && grassPosition.precedes(jungleCords[1]))
                    jungleGrassCounter++;
                else
                    savannaGrassCounter++;
            }
            if(i < jungleArea) {
                assertEquals(savannaGrassCounter, jungleGrassCounter, "savvana : jungle not even grass distribution");
                assertEquals(savannaGrassCounter, i + 1, "sth weird happened");
            }
            else if(i < (width * height) - jungleArea){
                assertEquals(jungleArea, jungleGrassCounter, "jungle added despite being full");

                assertEquals(i+1, savannaGrassCounter, "savanna not added whenit should");
            }
            else {
                assertEquals(width * height - jungleArea, savannaGrassCounter, "savanna added despite being full");
            }
        }

    }

    @Test
    public void grassGenTest1(){
        int width = 47, height = 20, jungleRatio = 4, savannaRatio = 23;
        RectangularMap map = new RectangularMap(width,height,jungleRatio,savannaRatio);
        Vector2d[] jungleCords = map.jungleSize;
        int jungleArea = (jungleCords[1].x - jungleCords[0].x + 1) * (jungleCords[1].y - jungleCords[0].y + 1);
        HashMap<Vector2d,IMapElement> grassMap;
        int savannaGrassCounter, jungleGrassCounter;
        for(int i = 0; i < width*height; i++){
            savannaGrassCounter = 0; jungleGrassCounter = 0;
            map.addGrass();
            grassMap = map.getObjects();
            for(Vector2d grassPosition : grassMap.keySet()){
                if(grassPosition.follows(jungleCords[0]) && grassPosition.precedes(jungleCords[1]))
                    jungleGrassCounter++;
                else
                    savannaGrassCounter++;
            }
            if(i < jungleArea) {
                assertEquals(savannaGrassCounter, jungleGrassCounter, "savvana : jungle not even grass distribution");
                assertEquals(savannaGrassCounter, i + 1, "sth weird happened");
            }
            else if(i < (width * height) - jungleArea){
                assertEquals(jungleArea, jungleGrassCounter, "jungle added despite being full");

                assertEquals(i+1, savannaGrassCounter, "savanna not added whenit should");
            }
            else {
                assertEquals(width * height - jungleArea, savannaGrassCounter, "savanna added despite being full");
            }
        }

    }

    @Test//test with bigger jungle than savanna
    public void grassGenTest2(){
        int width = 35, height = 69, jungleRatio = 17, savannaRatio = 3;
        RectangularMap map = new RectangularMap(width,height,jungleRatio,savannaRatio);
        Vector2d[] jungleCords = map.jungleSize;
        int jungleArea = (jungleCords[1].x - jungleCords[0].x + 1) * (jungleCords[1].y - jungleCords[0].y + 1);
        int savannaArea = width*height - jungleArea;
        HashMap<Vector2d,IMapElement> grassMap;
        int savannaGrassCounter, jungleGrassCounter;
        for(int i = 0; i < Math.max(jungleArea,savannaArea) + 5; i++){
            savannaGrassCounter = 0; jungleGrassCounter = 0;
            map.addGrass();
            grassMap = map.getObjects();
            for(Vector2d grassPosition : grassMap.keySet()){
                if(grassPosition.follows(jungleCords[0]) && grassPosition.precedes(jungleCords[1]))
                    jungleGrassCounter++;
                else {
                    savannaGrassCounter++;
                }
            }
            if(i < savannaArea) {
                assertEquals(savannaGrassCounter, jungleGrassCounter, "savvana : jungle not even grass distribution i = " + i);
                assertEquals(savannaGrassCounter, i + 1, "sth weird happened");
            }
            else if(i < jungleArea){
                assertEquals(savannaArea, savannaGrassCounter, "savanna added despite being full");
                assertEquals(i+1, jungleGrassCounter, "jungle not added when it should");
            }
            else {
                assertEquals(jungleArea,jungleGrassCounter, "jungle added despite being full");
            }
        }

    }

    @Test//test with bigger jungle than savanna
    public void randGrassGenTest(){
        Random rand = new Random();
        int width = rand.nextInt(200) + 1, height = rand.nextInt(200) + 1, jungleRatio = rand.nextInt(200) + 1, savannaRatio = rand.nextInt(200) + 1;
        //int width = 161, height = 109, jungleRatio = 119, savannaRatio = 131;
        System.out.println("Rand params width: " + width + " height: " + height + " jungleRatio: " + jungleRatio + " savannaRatio: " + savannaRatio);
        RectangularMap map = new RectangularMap(width,height,jungleRatio,savannaRatio);
        Vector2d[] jungleCords = map.jungleSize;
        //System.out.println(jungleCords[0]+ " "+ jungleCords[1]);
        int jungleArea = (jungleCords[1].x - jungleCords[0].x + 1) * (jungleCords[1].y - jungleCords[0].y + 1);
        int savannaArea = width*height - jungleArea;
        HashMap<Vector2d,IMapElement> grassMap;
        int savannaGrassCounter, jungleGrassCounter;
        for(int i = 0; i < Math.max(jungleArea,savannaArea) + 10; i++){
            savannaGrassCounter = 0; jungleGrassCounter = 0;
            map.addGrass();
            grassMap = map.getObjects();
            for(Vector2d grassPosition : grassMap.keySet()){
                if(grassPosition.follows(jungleCords[0]) && grassPosition.precedes(jungleCords[1]))
                    jungleGrassCounter++;
                else
                    savannaGrassCounter++;
            }
            //System.out.println(savannaGrassCounter);
            if(i < Math.min(jungleArea,savannaArea)) {
                assertEquals(savannaGrassCounter, jungleGrassCounter, "savvana : jungle not even grass distribution");
                assertEquals(savannaGrassCounter, i + 1, "sth weird happened");
            }
            else if(i < (width * height) - Math.min(jungleArea,savannaArea)){
                if(jungleArea <= savannaArea) {
                    assertEquals(jungleArea, jungleGrassCounter, "jungle added despite being full 0");
                    assertEquals(i + 1, savannaGrassCounter, "savanna not added whenit should 0");
                }
                else {
                    assertEquals(savannaArea, savannaGrassCounter, "savanna added despite being full 1");
                    assertEquals(i+1, jungleGrassCounter, "jungle not added when it should 1");
                }
            }
            else {
                if(jungleArea <= savannaArea)
                    assertEquals(width * height - jungleArea, savannaGrassCounter, "savanna added despite being full 2");
                else
                    assertEquals(width * height - savannaArea, jungleGrassCounter, "jungle added despite being full 2");
            }
        }

    }


}
