package Database;

import Gameplay.Map;

public class DBMapLoader extends MapLoader {

  private static final DatabaseInterface db = new SQLiteInterface(".lightracer.db");

  public DBMapLoader(String mapName) {
    name = mapName;
  }

  protected String openMap(){
    return null;
  }

}
