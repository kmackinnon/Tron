package Database;

import java.util.ArrayList;

public abstract class DatabaseInterface {

  protected ArrayList<String> supportList;

    
  public abstract User getUser(String username);

  public abstract void updateUser(int uid, UserStatistics stats);

  public abstract User addUser(String username, String password);

  public abstract void addGame(GameInfo game);

  public abstract UserStatistics getUserStats(int uid);

  public abstract void changePassword(int uid, String newPassword);

  public boolean supports(String capability) {
    return supportList.contains(capability);
  }
  public String[] getSupportList(){
    return supportList;
  }

  public abstract void addMap(String name, String mapString);

  public abstract String getMap(String name);

}
