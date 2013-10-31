package Database;

import String;


public abstract class DatabaseInterface {

  protected String[] supportList;

    
  protected abstract User getUser(String username);

  protected abstract void updateUser(int uid, UserStatistics stats);

  protected abstract User addUser(String username, String password);

  protected abstract void addGame(Game game);

  protected abstract UserStatistics getUserStats(int uid);

  public abstract void changePassword(int uid, String newPassword) {
  }

  public boolean supports(String capability) {
    return null;
  }
  public String[] getSupportList(){
    return supportList;
  }

  public abstract void addMap(String name, String mapString);

  public abstract String getMap(String name);

}
