package Database;

import Gameplay.Map;
import UserInterface.Display;

public abstract class MapLoader {

  protect String name;

    
  public Map loadMap(Display display, GameInfo game) {
    return new Map(openMap(), "0x000", game, display);
  }

  protected abstract String openMap();

}
