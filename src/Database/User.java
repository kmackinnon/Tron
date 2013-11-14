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
    if (db.confirmUser(this.uid, password)){
      authenticated = true;
      //TODO actually log the user in...
    } else {
      authenticated = false;
    }
  }

  public static User create(String username, String password) {
    return db.addUser(username, password);
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
