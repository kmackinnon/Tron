package Database;

import Gameplay.Map;
import UserInterface.Display;

public abstract class MapLoader {

  protected String name;
    
  public abstract Map loadMap(Display display, GameInfo game);

  protected abstract DatabaseInterface.MapSpecs openMap();

}
