package Database;

import java.util.Vector;
import Gameplay.Map;

public class Game {

  private int playerOneWins;

  private int playerTwoWins;

    public Map myMap;
    /**
   * 
   * @element-type Player
   */
  public Vector  myPlayer;
    /**
   * 
   * @element-type MapLoader
   */
  public Vector  myMapLoader;
    public DatabaseInterface myDatabaseInterface;

  public void startRound() {
  }

}