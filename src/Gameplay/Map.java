package Gameplay;

import Database.Game;
import UserInterface.Display;
import BitSet;
import String;

public class Map {

  private BitSet map;
  private int width;
  private int height;

  private Game myGame;
  private Display myDisplay;
  
  public void addWall(int xPos, int yPos, String colour) {
    map.set(getCellIndex(xPos,yPos));
  }

  private boolean outside(int x, int y){
    return (x >= width || x < 0 || y >= height || y < 0);   
  }

  private int getCellIndex(int x, int y){
    return y * width + x;
  }

  public boolean collides(int x, int y) {
    return (outside(x,y)||map.get(getCellIndex(x,y)));
  }

}
