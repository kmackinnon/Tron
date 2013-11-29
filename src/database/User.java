package database;

/**
 * Class creates a User object which has many useful methods for getting
 * important information such as login info as well as statistics.
 *
 * @author Michael Williams
 */
public class User {

    private final int uid;

    private final String username;
    private UserStatistics myStats;
    private static final DatabaseInterface db = new SQLiteInterface(".lightracer.db");
    private boolean authenticated;

    /**
     * Internal Constructor used by the Database when called by User's Static
     * functions getUser and createUser.
     *
     * @param uid Internal User ID
     * @param username Username
     */
    public User(int uid, String username) {
        this.uid = uid;
        this.username = username;
        myStats = db.getUserStats(uid);
        if (myStats == null) { //User has no stats
            myStats = new UserStatistics(0, 0, 0);
            db.createStats(uid);
        }
    }

    /**
     * Checks whether a user has been authenticated. If so, log him in.
     *
     * @param username
     * @param password
     * @return the User object based on his username
     */
    public static User getUser(String username, String password) {
        User user = db.getUser(username);

        // ensure that there is a user in the db
        if (user != null) {
            user.login(password);
            if (user.authenticated) {
                return user;
            }
        }
        // return null if user not in db or not authenticated
        return null;

    }

    public static User createUser(String username, String password) {
        return db.addUser(username, password);
    }

    /**
     * For future functionality if we wanted to implement it.
     *
     * @param password
     */
    public void changePassword(String password) {
        db.changePassword(this.uid, password);
    }

    /**
     * Set the authenticated field to true or false based on a login
     *
     * @param password
     */
    public void login(String password) {
        authenticated = db.confirmUser(this.uid, password);
    }

    /**
     * Update statistics by adding a win.
     */
    public void winGame() {
        this.myStats.addWin();
    }

    /**
     * Update User's statistics by adding a loss.
     */
    public void loseGame() {
        this.myStats.addLoss();
    }

    /**
     * @return player's username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * @return user's id
     */
    public int getID() {
        return this.uid;
    }

    /**
     * @return all user statistics
     */
    public UserStatistics getStats() {
        return myStats;
    }

    /**
     * @return true or false depending on if user is authenticated by db
     */
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * @return the top ten players by total wins in the database
     */
    public static String[][] getTopTen() {
        return db.getTopTenPlayerStats();
    }

    /**
     * @return the number of games won by a User
     */
    public int getGamesWon() {
        return myStats.getWins();
    }

    /**
     * @return the number of games a User has played
     */
    public int getGamesPlayed() {
        return myStats.getGames();
    }

    /**
     * @return the number of games a User has lost
     */
    public int getGamesLost() {
        return myStats.getLosses();
    }

    /**
     * Updates the database with new statistics.
     */
    public void saveStats() {
        db.updateUser(uid, myStats);
    }

    /**
     * @param user1
     * @param user2
     * @return the head-to-head score of two players
     */
    public static int[] getHead2HeadStats(User user1, User user2) {
        return db.getHead2Head(user1, user2);
    }
}
