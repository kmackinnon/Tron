package database;

import gameplay.Map;
import userinterface.Display;

/**
 * Class which allows subclasses to load specific maps with game information.
 *
 * @author Michael Williams
 */
public abstract class MapLoader {

    /**
     * Map name. Used for Identification in several storage backends. For files
     * this would be the filename with the extension removed.
     */
    protected String name;

    /**
     * Loads Map using Implementation specific backend. Returned Map is then
     * ready to play on.
     *
     * @param display Display to show the map on.
     * @param game Game this map is associated with.
     * @return New Map to Play on.
     */
    public abstract Map loadMap(Display display, GameInfo game);

    /**
     * Pulls the map Specs for the Map to load from, should it be in Binary
     * format. Parsed String based formats will not need to use this function.
     *
     * @return Info on the map.
     */
    protected abstract DatabaseInterface.MapSpecs openMap();

}
