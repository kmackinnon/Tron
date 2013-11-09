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


/**
 * Contains details concerning the current map in play, and determines if a collision has occurred.
 * @author draringi
 */
public class Map {

  private static final Pattern headerParser = Pattern.compile("\\dx\\d");

  private BitSet map;
  private int width;
  private int height;
  private int numOfPlayers;
  private boolean running;
  private int sleep;


  private Vector<Player> playerList;
  private final Game myGame;
  private final Display myDisplay;
  private static Controller controller;
  
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

  public void setSpeed(int hz){
    sleep = 1000/hz;
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
  
  public static Map makeDemo(Display display){
    Map map = new Map(50, 50, null, display);
    map.addPlayer(null, "0xF00");
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
    gameLoop();
    myDisplay.gameover();
  }

  public Player getPlayer(int i){
    return playerList.get(0);
  }

  private void gameLoop(){
    running = true;
    Iterator<Player> it;
    while(running){
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
        
      }
    }
  }

  public void addPlayer(User user, String colour, int direction){//TODO: change direction to enum
    numOfPlayers++;
    Player player = new Player(25, 25, colour, user, this, 1);/TODO: change direction to enum
    playerList.add(player);
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
    playerList = new Vector();
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
}
