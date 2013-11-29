package database;

import gameplay.Map;
import userinterface.Display;

/**
 * Class which allows subclasses to load specific maps with game information.
 * @author Michael Williams 
 */
public abstract class MapLoader {

  protected String name;
    
  public abstract Map loadMap(Display display, GameInfo game);

  protected abstract DatabaseInterface.MapSpecs openMap();

}
