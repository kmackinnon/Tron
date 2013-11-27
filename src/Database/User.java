package Database;

public class User {

    private int uid;
    
    private String username;
    private UserStatistics myStats;
    private final DatabaseInterface db = new SQLiteInterface(".lightracer.db");
    private boolean authenticated;
    private boolean success;

    /**
     * 
     * @param username
     * @param password
     * @param newUser 
     */
    public User(String username, String password, boolean newUser) {
        if (newUser && !db.userExists(username)  ) {
            uid = db.addUser(username, password);
            if (uid < 0){
                return;
            }
            this.username = username;
            success = true;
        } else if (!newUser && db.userExists(username)) {
            uid = db.getUser(username);
            if (uid > 0 && db.confirmUser(uid, password)) {
                success = true;
                this.username = username;
            }
        } 
    }

    /**
     * For future functionality if we wanted to implement it.
     * @param password 
     */
    public void changePassword(String password) {
        db.changePassword(this.uid, password);
    }

    /**
     * Set the authenticated field to true or false based on a login
     * @param password 
     */
    public void login(String password) {
        authenticated = db.confirmUser(this.uid, password);
    }

    public void save() {
        db.updateUser(this.uid, this.myStats);
    }

    public void win() {
        this.myStats.addWin();
    }

    public void lose() {
        this.myStats.addLoss();
    }    
    
    // Accessors
    public String getUsername() {
        return this.username;
    }
    
    public UserStatistics getStats() {
        return myStats;
    }
        
    public boolean getAuthenticated() {
        return authenticated;
    }
    
    public boolean getSuccess() {
        return success;
    }
}
