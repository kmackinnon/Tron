package Database;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
import Gameplay.Map;
import Gameplay.Player;

public class GameInfo {

  private int winner;

  private Map current;
  private MapLoader baseMap;
  

    /**
   * 
   * @element-type Player
   */
  private final ArrayList<Player>  playerList;
    /**
   * 
   * @element-type MapLoader
   */
  private static final DatabaseInterface myDatabaseInterface = new SQLiteInterface(".lightracer.db");
  private int speed;

  
  public GameInfo (User playerOne, String playerOneColour, User playerTwo, String playerTwoColour, int speed, MapLoader map){
    winner = -1;
    this.speed = speed;
    playerList = new ArrayList(2);
    this.baseMap = map;
    playerList.add(new Player(playerOne, playerOneColour));
    playerList.add(new Player(playerTwo, playerTwoColour));
  }
  
  /**
   * 
   */
  public void startRound() {
    current = baseMap.loadMap();
    current.addPlayer(playerList.get(0), Player.Direction.UP, 24, 24);
    current.addPlayer(playerList.get(1), Player.Direction.DOWN, 26, 26);
    current.run();
  }
  
  public void endRound(boolean draw, Player victor){
    if ( draw ) {
      current = currentMapInit.loadMap();
      current.addPlayer(playerList.get(0), Player.Direction.UP, 24, 24);
      current.addPlayer(playerList.get(1), Player.Direction.DOWN, 26, 26);
      current.run();
    } else {
      victor.winRound();
      Iterator<Player> it;
      Player p;
      int i = 0;
      for (it = playerList.iterator(); it.hasNext();){
        p = it.next();
        if (p.getWins()>=2){
          winner = i;
          return;
        }
        i++;
      }
      this.startRound();
    }
  }
  
  public boolean endGame(){
    if (winner < 0) {
      return false;
    } else {
      Player player = playerList.get(winner);
      player.winGame();
      return true;
    }
  }
  
  public String getPlayerName(int index){
    Player player = playerList.get(index);
    return player.getUsername();
  }
  
  public String getWinnerName(){
    if (winner < 0) {
      return null;
    } else {
      return getPlayerName(winner);
    }
  }

}
