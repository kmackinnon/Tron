/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Gameplay.Map;
import UserInterface.Display;

/**
 * Development class for generating Maps files in Binary format.
 * @author Michael Williams
 */
public class MapGenerator {
    private Display display;
    private static final DatabaseInterface db = new SQLiteInterface(".lightracer.db");
    private Map basic1;
    private Map basic2;
    private Map basic3;
    
    MapGenerator (Display display){
        this.display = display;
    }
    
    public void makeMap1 (){
        basic1 = new Map(75, 50, null, display);
    }
    
    public void saveMap1 (){
        db.addMap("BasicMap1", basic1.getBinaryData(), basic1.getWidth(), basic1.getHeight());
    }
    public void makeMap2 () {
        basic2 = new Map(75, 50, null, display);
        for (int i = 15; i < 25; i++) {
            for (int j = 20; j < 30; j++) {
                basic2.addWall(i, j, "0x000");
            }
        }
        for (int i = 50; i < 60; i++) {
            for (int j = 20; j < 30; j++) {
                basic2.addWall(i, j, "0x000");
            }
        }
    }
    
    public void saveMap2 (){
        db.addMap("BasicMap2", basic2.getBinaryData(), basic2.getWidth(), basic2.getHeight());
    }
    
    public void makeMap3 () {
        basic3 = new Map(75, 50, null, display);
        for (int i = 30; i < 45; i++) {
            for (int j = 20; j < 30; j++) {
                basic3.addWall(i, j, "0x000");
            }
        }
        for (int i = 5; i < 25; i++) {
            for (int j = 25; j < 45; j++) {
                basic3.addWall(i, j, "0x000");
            }
        }
        for (int i = 50; i < 70; i++) {
            for (int j = 5; j < 25; j++) {
                basic3.addWall(i, j, "0x000");
            }
        }
    }
    
    public void saveMap3 (){
        db.addMap("BasicMap3", basic3.getBinaryData(), basic3.getWidth(), basic3.getHeight());
    }
}
