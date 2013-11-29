package database;

import gameplay.Map;
import userinterface.Display;

/**
 * Loads maps into the database. 
 * @author Michael Williams
 */
public class DBMapLoader extends MapLoader {

  private static final DatabaseInterface db = new SQLiteInterface(".lightracer.db");

  /**
   * @param mapName 
   */
  public DBMapLoader(String mapName) {
    name = mapName;
  }
  
  /**
   * @param display
   * @param game
   * @return a new map containing all necessary information pertaining to a Map
   */
  @Override
  public Map loadMap(Display display, GameInfo game){
      DatabaseInterface.MapSpecs mapData = openMap();
      return new Map(mapData.width, mapData.height, mapData.data, "0xEEE", game, display );
  }

  /**
   * @return the map from the database
   */
  @Override
  protected DatabaseInterface.MapSpecs openMap(){
    return db.getMap(name);
  }

}
