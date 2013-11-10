package Gameplay;

import Database.Game;
import Database.User;
import UserInterface.Display;

import java.util.BitSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Vector;
import java.util.Iterator;

import java.awt.event.KeyEvent;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Contains details concerning the current map in play, and determines if a collision has occurred.
 * @author draringi
 */
public class Map {

  private static final Pattern headerParser = Pattern.compile("\\dx\\d");

  private MapTask internal;
  
  private BitSet map;
  private int width;
  private int height;

  private final Game myGame;
  private final Display myDisplay;
  private static Controller controller;
  
  /**
   * Adds a wall at a given co-ordinate with a given colour.
   * @param xPos
   * @param yPos
   * @param colour 
   */
  public void addWall(int xPos, int yPos, String colour){
    return internal.addWall(xPos, yPos, colour);
  }

  /**
   * Sets the speed of the map to a frequency hz given in Hertz
   * @param hz
   */
  public void setSpeed(int hz){
    return internal.setSpeed(hz);
  }

  /**
   * Checks if a collision would occur at a given co-ordinate.
   * @param x
   * @param y
   * @return 
   */
  public boolean collides(int x, int y) {
    return internal.collides(x, y);
  }

  private void mapParse(String mapString, String colour){
    Matcher m = headerParser.matcher(mapString);
  }
  
  public static Map makeDemo(Display display){
    Map map = new Map(50, 50, null, display);
    map.addPlayer(null, "0xF00", 1); //TODO: change direction to enum
    Player player = map.getPlayer(0);
    controller.addBinding(new MovePlayerDown(KeyEvent.VK_DOWN, player));
    controller.addBinding(new MovePlayerLeft(KeyEvent.VK_LEFT, player));
    controller.addBinding(new MovePlayerUp(KeyEvent.VK_UP, player));
    controller.addBinding(new MovePlayerRight(KeyEvent.VK_RIGHT, player));
    player.moveLeft();
    map.setSpeed(2);
    return map;
  }

  public void run(){
    new Thread(internal).start();
  }


  public void addPlayer(User user, String colour, int direction){ //TODO: change direction to enum
    Player player = new Player(25, 25, colour, user, this, direction);
    playerList.add(player);
  }

  /**
   * Generates Map from string 
   * @param mapString string formated map
   * @param colour String containing colour of base walls in hexdecimal format
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
    numOfPlayers = 0;
    map = new BitSet(width*height);
    playerList = new Vector();
    if (controller == null) {
      controller = new Controller();
    } else {
      controller.clear();
    }
  }

  private class MapTask extends Task<Void> {
    private BitSet map;
    private int width;
    private int height;
    private boolean running;
    private int sleep;  
    private Vector<Player> playerList;
    private final Game myGame;
    private final Display myDisplay;
    private final Map parent;
    
    public void addWall(int xPos, int yPos, String colour) {
      map.set(getCellIndex(xPos,yPos));
      myDisplay.displayWall(xPos, yPos, colour);
    }
    
    public MapTask (int width, int height, Game game, Display display, BitSet map, Map parent){
      myGame = game;
      myDisplay = display;
      this.width=width;
      this.height=height;
      this.map = map;
      playerList = new Vector();
      this.parent = parent;
    }

    @Override
    public Void call() {
      running = true;
      while(running){
        if(isCancelled()) {
          break;
        }
        listenPlayers();
        gameRound();
      }
      myDisplay.gameover();
      return null;
    }

    public void setSpeed(int hz){
      sleep = 1000/hz;
    }

    private int getCellIndex(int x, int y){
      return y * width + x;
    }

    private boolean outside(int x, int y){
      return (x >= width || x < 0 || y >= height || y < 0);   
    }

    public Player getPlayer(int i){
      return playerList.get(0);
    }

    private void gameRound(){
      Iterator<Player> it;
      for(it = playerList.iterator(); it.hasNext();){
        Player player = it.next();
        player.moveCurrent();
      }
      for(it = playerList.iterator(); it.hasNext();){
        Player player = it.next();
        if(collides(player.getX(), player.getY())){
          running = false;//TODO: add player dies code once Player has alive attribute...
        }
      }
      //TODO: add checks for who is alive
      try{
        Thread.sleep(sleep);
      } catch (InterruptedException ex) {
        if(isCancelled()) {
          break;
        }
      }
    }

    public void addPlayer(User user, String colour, int direction){//TODO: change direction to enum
      Player player = new Player(25, 25, colour, user, parent, direction);
      playerList.add(player);
    }

    public boolean collides(int x, int y) {
      return (outside(x,y)||map.get(getCellIndex(x,y)));
    }
  }
}
