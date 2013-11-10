package Gameplay;

import Database.Game;
import Database.User;
import UserInterface.Display;

import java.util.BitSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Vector;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;


import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;

/**
 * Contains details concerning the current map in play, and determines if a collision has occurred.
 * @author draringi
 */
public class Map {

  private static final Pattern headerParser = Pattern.compile("\\dx\\d");
  
  private final MapTask internal;
  private int width, height;
  
  private final static Controller controller = Controller.getInstance();
  
  /**
   * Adds a wall at a given co-ordinate with a given colour.
   * @param xPos
   * @param yPos
   * @param colour 
   */
  public void addWall(int xPos, int yPos, String colour){
    internal.addWall(xPos, yPos, colour);
  }

  /**
   * Sets the speed of the map to a frequency hz given in Hertz
   * @param hz
   */
  public void setSpeed(int hz){
    internal.setSpeed(hz);
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

  private BitSet mapParse(String mapString, String colour){
    Matcher m = headerParser.matcher(mapString);
    String head[];
    if(m.find()){
      head = m.group().split("x");
      m.find();
      width = Integer.parseInt(head[0]);
      height = Integer.parseInt(head[1]);
    }
    BitSet map = new BitSet();
    return map;
  }
  
  public static Map makeDemo(Display display){
    Map map = new Map(50, 50, null, display);
    map.addPlayer(null, "0xF00", 1); //TODO: change direction to enum
    Player player = map.getPlayer(0);
    controller.addBinding(new MovePlayerDown(KeyCode.S, player));
    controller.addBinding(new MovePlayerLeft(KeyCode.A, player));
    controller.addBinding(new MovePlayerUp(KeyCode.W, player));
    controller.addBinding(new MovePlayerRight(KeyCode.D, player));
    player.moveLeft();
    map.setSpeed(2);
    return map;
  }

  public void run(){
    new Thread(internal).start();
  }
  public Player getPlayer(int i){
    return internal.getPlayer(i);
  }

  public void addPlayer(User user, String colour, int direction){ //TODO: change direction to enum
    internal.addPlayer(user, colour, direction);
  }

  /**
   * Generates Map from string 
   * @param mapString string formated map
   * @param colour String containing colour of base walls in hexdecimal format
   * @param game
   * @param display 
   */
  public Map(String mapString, String colour, Game game, Display display){
    BitSet map = mapParse(mapString, colour);
    internal = new MapTask(width, height, game, display, map, this);
    controller.clear();
  }
  
  /**
   * Generates blank Map of given width and height
   * @param width
   * @param height
   * @param game
   * @param display 
   */
  public Map(int width, int height, Game game, Display display){
    this.width = width;
    this.height = height;
    internal = new MapTask(width, height, game, display, new BitSet(width*height), this);
    controller.clear();
  }

  private class MapTask extends Task<Void> {
    private final BitSet map;
    private final int width;
    private final int height;
    private boolean running;
    private int sleep;  
    private final Vector<Player> playerList;
    private final Game myGame;
    private final Display myDisplay;
    private final Map parent;
    private final ConcurrentLinkedQueue<Move> moveQueue;
    
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
      moveQueue = new ConcurrentLinkedQueue();
    }

    @Override
    public Void call() {
      running = true;
      while(running){
        if(isCancelled()) {
          break;
        }
        gameRound();
      }
      myDisplay.gameover();
      return null;
    }

    private void listenPlayers(){
      Move move;
      while (!moveQueue.isEmpty()){
        move = moveQueue.poll();
        map.set(getCellIndex(move.getX(),move.getY()));
        myDisplay.displayWall(move.getX(),move.getY(), move.getColour());
      }
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
      listenPlayers();
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
    
    private class Move {
      private final int x, y;
      private final String colour;
    
      public Move (int x, int y, String colour){
        this.x = x;
        this.y = y;
        this.colour = colour;
      }
      
      public final int getX(){
        return x;
      }
    
      public final int getY(){
        return y;
      }
    
      public final String getColour(){
        return colour;
      }
    }
  }
}
