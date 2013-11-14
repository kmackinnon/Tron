package Database;

import java.sql.*;

public class SQLiteInterface extends DatabaseInterface {
  Connection connection;
  Statement statement;
  Hash hash;

  public SQLiteInterface (String db) {
    connection = null;
     try {
       Class.forName("org.sqlite.JDBC");
       connection = DriverManager.getConnection("jdbc:sqlite:" + db);
       hash = new SHA256Hash();
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
    } catch(SQLException e) {
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
        String salt = result.getString("salt");
        result.close();
        statement.close();
        return correct.equals(hash.hash(password, salt));
      } else {
        result.close();
        statement.close();
        return false;
      }
    } catch (SQLException e){
      return false;
    }
  }
  
  @Override
  public void updateUser(int uid, UserStatistics stats) throws UnsupportedOperationException{
    //TODO: Implement this
  }
  
  @Override
  public User addUser(String username, String password){
    try {
      statement = connection.createStatement();
      String salt = Hash.genSalt(20);
      String hashedPassword = hash.hash(password,salt);
      statement.executeUpdate("insert into Users (username,password,salt) values ('"+username+"', '"+hashedPassword+"', '"+salt+"');");
      statement.close();
      return getUser(username);
    } catch (SQLException e){
      return null;
    }
  }
  
  @Override
  public void addGame(GameInfo game) throws UnsupportedOperationException{
    //TODO: Implement this
  }
  
  @Override
  public UserStatistics getUserStats(int uid) throws UnsupportedOperationException{
    return null;//TODO: Implement this
  }
  
  @Override
  public void changePassword(int uid, String newPassword){
    String salt = Hash.genSalt(20);
    String hashedPassword = hash.hash(newPassword, salt);
    try {
      statement = connection.createStatement();
      statement.executeUpdate("update Users set password = '"+hashedPassword+"', salt = '"+salt+"' where id = "+uid +";");
    } catch (SQLException e){
      
    }
  }
  
  @Override
  public void addMap(String name, String mapString) throws UnsupportedOperationException{
    throw new UnsupportedOperationException();
  }
  
  @Override
  public String getMap(String name) throws UnsupportedOperationException{
    return null; //TODO: have this throw an implementation error if not implemented
  }
}
