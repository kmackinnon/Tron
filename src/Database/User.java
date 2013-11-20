package Database;

import Gameplay.Player;

public class User {

    private String username;
    private int uid;
    private UserStatistics myStats;
    private final DatabaseInterface db = new SQLiteInterface(".lightracer.db");
    private Player myPlayer;
    private boolean authenticated;
    boolean success = false;

    /**
     * 
     * @param username
     * @param password
     * @param newUser 
     */
    public User(String username, String password, boolean newUser) {
        if (newUser && !db.userExists(username)  ) {
            uid = db.addUser(username, password);
            //if (uid < 0){
            //    success = false;
            //    return;
            //}
            this.username = username;
            success = true;
        } else if (!newUser && db.userExists(username)) {
            uid = db.getUser(username);
            if (uid > 0 && db.confirmUser(uid, password)) {
                success = true;
            }
        } 
    }

    public boolean getSuccess() {
        return success;
    }

    public String getUsername() {
        return this.username;
    }

    public void changePassword(String password) {
        db.changePassword(this.uid, password);
    }

    public void login(String password) {
        if (db.confirmUser(this.uid, password)) {
            authenticated = true;
            //TODO actually log the user in...
        } else {
            authenticated = false;
        }
    }

    public void save() {
        db.updateUser(this.uid, this.myStats);
    }

    //TODO how to set wins and losses
    public void win() {
        this.myStats.addWin();
    }

    public void lose() {
        this.myStats.addLoss();
    }

    public UserStatistics getStats() {
        return myStats;
    }
}
