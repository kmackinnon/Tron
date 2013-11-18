package Database;

import java.util.Vector;
import Gameplay.Map;

public class GameInfo {

  private int playerOneWins;

  private int playerTwoWins;

    private Map myMap;
    /**
   * 
   * @element-type Player
   */
  private Vector  myPlayer;
    /**
   * 
   * @element-type MapLoader
   */
  private Vector  myMapLoader;
  private static final DatabaseInterface myDatabaseInterface = new SQLiteInterface(".lightracer.db");

  /**
   * 
   */
  public void startRound() {
  }

}
