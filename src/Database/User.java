package Database;

public class User {

    private int uid;
    
    private String username;
    private UserStatistics myStats;
    private static final DatabaseInterface db = new SQLiteInterface(".lightracer.db");
    private boolean authenticated;
    private boolean success;

    /**
     * 
     * @param username
     * @param password
     * @param newUser 
     */
    public User (int uid, String username){
        this.uid = uid;
        this.username = username;
    }

    public static User getUser(String username, String password){
        User user = db.getUser(username);
        user.login(password);
        if (user.authenticated) {
            return user;
        } else {
            return null;
        }
    }
    
    public static User createUser(String username, String password){
        return db.addUser(username, password);
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
    
    public int getID(){
        return this.uid;
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
