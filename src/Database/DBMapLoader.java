package Database;

import Gameplay.Map;
import UserInterface.Display;

public class DBMapLoader extends MapLoader {

  private static final DatabaseInterface db = new SQLiteInterface(".lightracer.db");
  private int width, height;

  public DBMapLoader(String mapName) {
    name = mapName;
  }
  
  @Override
  public Map loadMap(Display display, GameInfo game){
      DatabaseInterface.MapSpecs mapData = openMap();
      return new Map(mapData.width, mapData.height, mapData.data, "0xEEE", game, display );
  }

  @Override
  protected DatabaseInterface.MapSpecs openMap(){
    return null;
  }

}
