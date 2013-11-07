package Gameplay;

import Database.Game;
import UserInterface.Display;

import java.util.BitSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Map {

  private static final Pattern headerParser = Pattern.compile("\\dx\\d");

  private BitSet map;
  private int width;
  private int height;

  private final Game myGame;
  private final Display myDisplay;
  
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

  public boolean collides(int x, int y) {
    return (outside(x,y)||map.get(getCellIndex(x,y)));
  }

  private void mapParse(String mapString, String colour){
    Matcher m = headerParser.matcher(mapString);
  }
  public Map(String mapString, String colour, Game game, Display display){
    myGame = game;
    myDisplay = display;
    mapParse(mapString, colour);
  }
}
