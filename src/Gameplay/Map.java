package Gameplay;

import Database.Game;
import UserInterface.Display;

import java.util.BitSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Contains details concerning the current map in play, and determines if a collision has occurred.
 * @author draringi
 */
public class Map {

  private static final Pattern headerParser = Pattern.compile("\\dx\\d");

  private BitSet map;
  private int width;
  private int height;

  private final Game myGame;
  private final Display myDisplay;
  
  /**
   * Adds a wall at a given co-ordinate with a given colour.
   * @param xPos
   * @param yPos
   * @param colour 
   */
  public void addWall(int xPos, int yPos, String colour) {
    map.set(getCellIndex(xPos,yPos));
    myDisplay.displayWall(xPos, yPos, colour);
  }

  private boolean outside(int x, int y){
    return (x >= width || x < 0 || y >= height || y < 0);   
  }

  private int getCellIndex(int x, int y){
    return y * width + x;
  }
  /**
   * Checks if a collision would occur at a given co-ordinate.
   * @param x
   * @param y
   * @return 
   */
  public boolean collides(int x, int y) {
    return (outside(x,y)||map.get(getCellIndex(x,y)));
  }

  private void mapParse(String mapString, String colour){
    Matcher m = headerParser.matcher(mapString);
  }
  
  /**
   * Generates Map from string 
   * @param mapString string formated map
   * @param colour
   * @param game
   * @param display 
   */
  public Map(String mapString, String colour, Game game, Display display){
    myGame = game;
    myDisplay = display;
    mapParse(mapString, colour);
  }
  
  /**
   * Generates blank Map of given width and height
   * @param width
   * @param height
   * @param game
   * @param display 
   */
  public Map(int width, int height, Game game, Display display){
      myGame = game;
      myDisplay = display;
      this.width = width;
      this.height = height;
      map = new BitSet(width*height);
  }
}
