package Database;

import java.util.Vector;
import Gameplay.Map;
import Integer;

public class Game {

  public Integer playerOneWins;

  public Integer playerTwoWins;

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