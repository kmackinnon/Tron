package database;

import java.util.ArrayList;

/**
 * Wrapper for the database to separate its implementation from other aspects of
 * the program. The Abstract DatabaseInterface exists to allow easy change to
 * different storage mediums in the future with little pain, even allowing the
 * listing of what features a given implementation supports and what it doesn't.
 *
 * @author Michael Williams
 */
public abstract class DatabaseInterface {

    /**
     * The support List contains strings stating what features an Implementation
     * supports. "User" is required at the bare minimum. Other possible entries
     * are: "Stats" "Games" "Maps"
     */
    protected ArrayList<String> supportList;

    /**
     * Must return the user with the given username. If the username is not
     * valid null is returned.
     *
     * @param username unique username
     * @return User Object for given username, or null if not available
     */
    public abstract User getUser(String username);

    /**
     * Confirms that a given password is correct for the user corresponding to
     * the given id. If the password is correct, it returns true, otherwise it
     * returns false.
     *
     * @param uid User ID
     * @param password User Password
     * @return Validity of password for given user
     */
    public abstract boolean confirmUser(int uid, String password);

    /**
     * Updates a given User's Statistics, providing the uid is valid, and the
     * feature is supported. If the uid is invalid, nothing happens. If the
     * feature is not supported, an UnsupportedOperationException is thrown.
     *
     * @param uid User ID
     * @param stats User's Statistics
     * @throws UnsupportedOperationException
     */
    public abstract void updateUser(int uid, UserStatistics stats) throws UnsupportedOperationException;

    /**
     * Creates empty Statistics entry for a given user.
     *
     * @param uid User ID
     * @throws UnsupportedOperationException
     */
    public abstract void createStats(int uid) throws UnsupportedOperationException;

    /**
     * Creates a new user in the Implementation's backend. Username must be
     * unique, otherwise null is returned.
     *
     * @param username Unique Username
     * @param password Password
     * @return Newly Created User, or null if username is not Unique
     */
    public abstract User addUser(String username, String password);

    /**
     * Checks if a user exists or not. That is, it checks if a username is not
     * unique.
     *
     * @param username Username to check.
     * @return False if username is unique, True if username is already in use.
     */
    public abstract boolean userExists(String username);

    /**
     * Adds a record of a finished game to the Implementation's data storage
     * medium. If Implementation does not support Game record saving this method
     * throws an UnsupportedOperationException.
     *
     * @param game Finished game to save
     * @throws UnsupportedOperationException
     */
    public abstract void addGame(GameInfo game) throws UnsupportedOperationException;

    /**
     * Retrieves Statistics for a given user. If ID is invalid, returns null. If
     * Implementation doesn't support Statistics, throws
     * UnsupportedOperationException.
     *
     * @param uid User ID
     * @return User's Statistics
     * @throws UnsupportedOperationException
     */
    public abstract UserStatistics getUserStats(int uid) throws UnsupportedOperationException;

    /**
     * Set's User's password to a new value.
     *
     * @param uid User ID
     * @param newPassword User's New Password
     */
    public abstract void changePassword(int uid, String newPassword);

    /**
     * Checks if the implementation supports a given feature.
     *
     * @param capability Feature to check support for.
     * @return Whether feature is supported.
     */
    public boolean supports(String capability) {
        return supportList.contains(capability);
    }

    /**
     * Returns List of Strings containing supported features.
     *
     * @return Supported Features List.
     */
    public ArrayList<String> getSupportList() {
        return supportList;
    }

    /**
     * Adds new map to the database. Name must be unique, otherwise method
     * silently fails. If Implementation does not support storing and loading of
     * maps, throws UnsupportedOperationException.
     *
     * @param name Name for the new map.
     * @param mapData Raw binary data for the layout of the map.
     * @param width Width of the map. width >= 0
     * @param height height of the map. height >= 0
     * @throws UnsupportedOperationException
     */
    public abstract void addMap(String name, byte mapData[], int width, int height) throws UnsupportedOperationException;

    /**
     * Retrieves map of given name from storage. If map isn't found returns
     * null. If Implementation does not support storing and loading of maps,
     * throws UnsupportedOperationException.
     *
     * @param name Name of Map to Load
     * @return Structure containing width, height and raw data..
     * @throws UnsupportedOperationException
     */
    public abstract MapSpecs getMap(String name) throws UnsupportedOperationException;

    /**
     * Retrieves the Top ten players by wins. Outputs as a 2d array of strings.
     * Returned string must be 2*10. 1st column is the username, 2nd column is
     * the number of wins. Each Row is a separate player.
     *
     * @return 2*10 array of strings with Top Ten Stats.
     * @throws UnsupportedOperationException
     */
    public abstract String[][] getTopTenPlayerStats() throws UnsupportedOperationException;

    /**
     * Retrieves comparative wins between two users. returned value will be an
     * array of ints of length 2. val[0] = Player One's Win count against Player
     * Two. val[1] = Player Two's Win count against Player One.
     *
     * @param user1 Player One
     * @param user2 Player Two
     * @return Vs. Win Count
     * @throws UnsupportedOperationException
     */
    public abstract int[] getHead2Head(User user1, User user2) throws UnsupportedOperationException;

    /**
     * Structural Class used for returning map data from the data storage medium
     * of the Implementations.
     */
    public class MapSpecs {

        /**
         * Width and Height of the Map.
         */
        public int width, height;

        /**
         * Raw Binary Data describing the layout of the map.
         */
        public byte data[];
    }
}
