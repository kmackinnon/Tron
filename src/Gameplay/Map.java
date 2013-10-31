package Gameplay;

import Database.Game;
import UserInterface.Display;
import Boolean;
import Integer;
import BitSet;
import String;

public class Map {

  private BitSet map;
  private int width;
  private int height;

  private Game myGame;
  private Display myDisplay;
  
  public void addWall(int xPos, int yPos, String colour) {
    map.set(getCellIndex(x,y));
  }

  private boolean outside(int x, int y){
    if (x >= width || x < 0 || y >= height || y < 0) {
      return true;
    } else {
      return false;
    }   
  }

  private int getCellIndex(int x, int y){
    return y * width + x
  }

  public boolean collides(int x, int y) {
    if (outside(x,y)){
      return true;
    } else if (map.get(getCellIndex(x,y))){
      return true;
    } else {
      return false;
    }
  }

}
