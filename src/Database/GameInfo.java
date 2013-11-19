package Database;

import java.util.ArrayList;
import Gameplay.Map;
import Gameplay.Player;

public class GameInfo {

  private int playerOneWins;

  private int playerTwoWins;

  private Map current;
    /**
   * 
   * @element-type Player
   */
  private ArrayList<Player>  myPlayer;
    /**
   * 
   * @element-type MapLoader
   */
  private ArrayList<MapLoader>  myMapLoader;
  private static final DatabaseInterface myDatabaseInterface = new SQLiteInterface(".lightracer.db");

  /**
   * 
   */
  public void startRound() {
  }

}
