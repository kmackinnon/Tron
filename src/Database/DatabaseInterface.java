package Database;

import java.util.ArrayList;

public abstract class DatabaseInterface {

  protected ArrayList<String> supportList;

    
  public abstract User getUser(String username);
  
  public abstract boolean confirmUser(int uid, String password);

  public abstract void updateUser(int uid, UserStatistics stats) throws UnsupportedOperationException;

  public abstract User addUser(String username, String password);
  
  public abstract boolean userExists(String username);

  public abstract void addGame(GameInfo game) throws UnsupportedOperationException;

  public abstract UserStatistics getUserStats(int uid) throws UnsupportedOperationException;

  public abstract void changePassword(int uid, String newPassword);

  public boolean supports(String capability) {
    return supportList.contains(capability);
  }
  public ArrayList<String> getSupportList(){
    return supportList;
  }

  public abstract void addMap(String name, byte mapData[], int width, int height) throws UnsupportedOperationException;

  public abstract MapSpecs getMap(String name) throws UnsupportedOperationException;
  
  public abstract String[][] getTopTenPlayerStats();

  public class MapSpecs{
      public int width, height;
      public byte data[];
  }
}
