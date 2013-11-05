package Database;

import Gameplay.Player;

public class User {

  private String username;

  private int uid;

  private UserStatistics myUserStatistics;
  private DatabaseInterface myDatabaseInterface;
  private Player myPlayer;
  
  public String getUsername(){
      return this.username;
  }
  public void login(String password) {
  }

  public static User create(String username, String password) {
  return null;
  }

  public void save() {
  }

  public void win() {
  }

  public void lose() {
  }

  public void changePassword(String newPassword) {
  }

  public UserStatistics getStats() {
  return null;
  }

}
