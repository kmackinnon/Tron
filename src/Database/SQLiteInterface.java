package Database;

import java.sql.*;
import java.security.SecureRandom;

public class SQLiteInterface extends DatabaseInterface {
  Connection connection;
  Statement statement;

  public SQLiteInterface (String db) {
    connection = null;
     try {
       Class.forName("org.sqlite.JDBC");
       connection = DriverManager.getConnection("jdbc:sqlite:" + db);
     } catch (Exception e) {
       // Bad things... Very Bad Things...
     }
     supportList.add("user");
  }
  
  @Override
  public User getUser(String username){
    try {
      statement = connection.createStatement();
      ResultSet result = statement.executeQuery("select * from Users where username = "+username+";");
      if (result.first()){
        int id = result.getInt("id");
        result.close();
        statement.close();
        return new User();
        //return new User(id, username);
      } else {
        result.close();
        statement.close();
        return null;
      }
    } catch(java.sql.SQLException e) {
      return null; //TODO: replace this with proper error handling...
    }
  }
  
  @Override
  public boolean confirmUser(int uid, String password){
    try {
      statement = connection.createStatement();
      ResultSet result = statement.executeQuery("select * from Users where uid = "+uid+";");
      if (result.first()){
        String correct = result.getString("password");
        String seed = result.getString("seed");
        result.close();
        statement.close();
        return correct.equals(hash(password, seed));
      } else {
        result.close();
        statement.close();
        return false;
      }
    } catch (java.sql.SQLException e){
      return false;
    }
  }
  
  @Override
  protected String hash(String string, String seed){
    return null; //TODO: Move into it's own class
  }
  
  @Override
  public void updateUser(int uid, UserStatistics stats){
    //TODO: Implement this
  }
  
  @Override
  public User addUser(String username, String password){
    try {
      statement = connection.createStatement();
      byte seed[];
      try{
        seed = SecureRandom.getInstance("SHA1PRNG").generateSeed(20);
      } catch (java.security.NoSuchAlgorithmException e){
        return null;
      }
      String hashedPassword = hash(password,new String(seed));
      statement.executeUpdate("insert into Users (username,password,seed) values ('"+username+"', '"+hashedPassword+"', '"+seed+"');");
      statement.close();
      return getUser(username);
    } catch (java.sql.SQLException e){
      return null;
    }
  }
  
  @Override
  public void addGame(GameInfo game){
    //TODO: Implement this
  }
  
  @Override
  public UserStatistics getUserStats(int uid){
    return null;//TODO: Implement this
  }
  
  @Override
  public void changePassword(int uid, String newPassword){
    //TODO: Implement this
  }
  
  @Override
  public void addMap(String name, String mapString){
    //TODO: have this throw an implementation error
  }
  
  @Override
  public String getMap(String name){
    return null; //TODO: have this throw an implementation error if not implemented
  }
}
