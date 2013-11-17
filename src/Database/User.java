package Database;

import Gameplay.Player;

public class User {

    private String username;
    private int uid;
    private UserStatistics myStats;
    private static DatabaseInterface db = new SQLiteInterface("mainDataBase");
    private Player myPlayer;
    private boolean authenticated;
    boolean success = false;

    public User(String username, String password, boolean newUser) {
        if (newUser &&!db.userExsits(username)  ) {
            uid = db.addUser(username, password);;
            this.username = username;
            success = true;
        } else if (!newUser && db.userExsits(username)) {
            uid = db.getUser(username);
            if (db.confirmUser(uid, password)) {
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
