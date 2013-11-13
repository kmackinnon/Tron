package Database;

import Gameplay.Player;

public class User {

  private String username;

  private int uid;

  private UserStatistics myStats;
  private static DatabaseInterface db;
  private Player myPlayer;
  private boolean authenticated;
  
  public String getUsername(){
    return this.username;
  }

  public void changePassword(String password){
    db.changePassword(this.uid, password);
  }

  public void login(String password) {
  }

  public static User create(String username, String password) {
    return db.addUser(username, password);
  }

  public void save() {
    db.updateUser(this.uid, this.myStats);
  }

  public void win() {
  }

  public void lose() {
  }

  public UserStatistics getStats() {
    return myStats;
  }

}
