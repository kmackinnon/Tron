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
  private DatabaseInterface myDatabaseInterface;

  private void startRound() {
  }

}
