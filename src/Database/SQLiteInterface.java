package Database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.iharder.Base64;

public class SQLiteInterface extends DatabaseInterface {

    private  Connection connection;
    private Statement statement;
    private final Hash hash;

    public SQLiteInterface(String db) {
        connection = null;
        supportList = new ArrayList();
        hash = new SHA256Hash();
        String homedir = System.getProperty("user.home");
        try {
            Class.forName("org.sqlite.JDBC");
            try {
             connection = DriverManager.getConnection("jdbc:sqlite:" + homedir + "/" + db);   
            } catch (SQLException ex) {
                Logger.getLogger(SQLiteInterface.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Something bad happened in opening db" + ex.getMessage());
                return;
            }
            try {
                statement = connection.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(SQLiteInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
            ResultSet result;
            try {
                result = statement.executeQuery("SELECT * FROM Users;");
                statement.close();
            } catch (SQLException e) { //If the Users table doesn't exist
                LinkedList<String> sqlCommands = new LinkedList();
                String line;
                try {
                    try (InputStream file = getClass().getResourceAsStream("/data/db_init.sql"); InputStreamReader reader = new InputStreamReader(file); BufferedReader buffer = new BufferedReader(reader)) {
                        while ( (line = buffer.readLine()) != null){
                            sqlCommands.add(line);
                        }
                    }
                    try (InputStream file = getClass().getResourceAsStream("/data/maps.sql"); InputStreamReader reader = new InputStreamReader(file); BufferedReader buffer = new BufferedReader(reader)) {
                        while ( (line = buffer.readLine()) != null){
                            sqlCommands.add(line);
                        }
                    }
                } catch (IOException ex) {
                    System.out.println("Something bad happened in reading sql" + ex.getMessage());
                    Logger.getLogger(SQLiteInterface.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
                String sql;
                try {
                    while ((sql = sqlCommands.poll()) != null) {
                        statement.execute(sql);
                    }
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SQLiteInterface.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
            }
        } catch (ClassNotFoundException  e) {
            System.out.println("Something bad happened in init" + e.getMessage());
        }
        supportList.add("user");
        supportList.add("map");
    }

    @Override
    public User getUser(String username) {
        try {
          statement = connection.createStatement();
          String encodedUsername = Base64.encodeBytes(username.getBytes());
          int id;
          String user;
          try (ResultSet result = statement.executeQuery("select * from Users where username = '" + encodedUsername + "';")) {
            id = result.getInt("id");
            user = result.getString("username");
            result.close();
          }
          statement.close();
          if (user == null) {
              return null;
          }
          return new User(id, username);
          //return id;
        } catch (SQLException e) {
          return null; //TODO: replace this with proper error handling...
        }
    }

    
    @Override
    public boolean userExists(String username) {
        try {
            statement = connection.createStatement();
            username = Base64.encodeBytes(username.getBytes());
            ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM Users WHERE username = '" + username + "';");
            if (result.getInt(1) == 1) {
                result.close();
                statement.close();
                return true;
            } else {
                result.close();
                statement.close();
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false; //TODO: replace this with proper error handling...
        }
    }

    @Override
    public boolean confirmUser(int uid, String password) {
        try {
            statement = connection.createStatement();
            String correct;
            String salt;
            try (ResultSet result = statement.executeQuery("select * from Users where id = " + uid + ";")) {
                correct = result.getString("password");
                salt = result.getString("salt");
                result.close();
            }
            statement.close();
            return correct.equals(hash.hash(password, salt));
        } catch (SQLException| NoSuchAlgorithmException e) {
            return false;
        }
    }

    @Override
    public void updateUser(int uid, UserStatistics stats) {
        try {
          statement = connection.createStatement();
          statement.executeUpdate("update UserStats set wins = " + stats.getWins() + ", losses = " + stats.getLosses() + ", games = " + stats.getGames() + " where user_id = " + uid + ";");
          statement.close();
        } catch (SQLException e) {
            Logger.getLogger(SQLiteInterface.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public User addUser(String username, String password) {
        try {
            statement = connection.createStatement();
            String realUsername = username;
            username = Base64.encodeBytes(username.getBytes());
            String salt;
            String hashedPassword;
            try{
                salt = Hash.genSalt(20);
                hashedPassword = hash.hash(password, salt);
            } catch ( NoSuchAlgorithmException e ){
                System.out.println("Something bad happened in hashing in creation" + e.getMessage());
                return null;
            }
            statement.executeUpdate("insert into Users (username,password,salt) values ('" + username + "', '" + hashedPassword + "', '" + salt + "');");
            statement.close();
            return getUser(realUsername);
        } catch (SQLException  e) {
            System.out.println("Something bad happened in creation" + e.getMessage());
            return null;
        }
    }

    @Override
    public void addGame(GameInfo game) throws UnsupportedOperationException {
        try {
          statement = connection.createStatement();
          statement.executeUpdate("INSERT INTO GameStats ( player_one, player_two, winner, player_one_rounds, player_two_rounds) VALUES( "+ game.getPlayerID(0) + ", "+ game.getPlayerID(1) + ", "+ game.getWinnerID() + ", " + game.getPlayerWins(0) + ", " + game.getPlayerWins(1) + ");");
          statement.close();
        } catch (SQLException ex) {
        Logger.getLogger(SQLiteInterface.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    @Override
    public UserStatistics getUserStats(int uid) throws UnsupportedOperationException {
      try {
        statement = connection.createStatement();
        int wins;
        int losses;
        int games;
        try (ResultSet result = statement.executeQuery("select * from UserStats where user_id = " + uid + ";")) {
          wins = result.getInt("wins");
          losses = result.getInt("losses");
          games = result.getInt("games");
          result.close();
        }
        statement.close();
        return new UserStatistics(wins, losses, games);
      } catch (SQLException e) {
        return null;
      }
    }

    @Override
    public void changePassword(int uid, String newPassword) {
        try {
          String salt = Hash.genSalt(20);
          String hashedPassword = hash.hash(newPassword, salt);
          statement = connection.createStatement();
          statement.executeUpdate("update Users set password = '" + hashedPassword + "', salt = '" + salt + "' where id = " + uid + ";");
          statement.close();
        } catch (SQLException | NoSuchAlgorithmException e) {
            System.out.println("Something bad happened");
        }
    }

    @Override
    public void addMap(String name, byte data[], int width, int height) throws UnsupportedOperationException {
        try {
            String mapData = Base64.encodeBytes(data);
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Maps (map_name, width, height, map_data) VALUES ('" + name + "', " + width + ", "+ height +", '" + mapData + "')");
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public DatabaseInterface.MapSpecs getMap(String name) throws UnsupportedOperationException {
        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM Maps WHERE map_name = '" + name + "';");           
            DatabaseInterface.MapSpecs specs = new DatabaseInterface.MapSpecs();
            specs.width = result.getInt("width");
            specs.height = result.getInt("height");
            specs.data = Base64.decode(result.getString("map_data"));
            result.close();
            statement.close();
            return specs;
        } catch (SQLException | IOException ex) {
            Logger.getLogger(SQLiteInterface.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public String[][] getTopTenPlayerStats() {
      String topTen[][] = new String[2][10];
      try {
        statement = connection.createStatement();
          try (ResultSet result = statement.executeQuery("SELECT username, wins FROM Users JOIN UserStats ON Users.id = UserStats.User_id ORDER BY wins DESC LIMIT 10;")) {
              int i = 0;
              while(result.next()){
                try {
                  int wins = result.getInt("wins");
                  String username = result.getString("username");
                  username = new String(Base64.decode(username));
                  topTen[0][i] = username;
                  topTen[1][i] = String.valueOf(wins);
                  i++;
                } catch ( SQLException | IOException ex) {
                    Logger.getLogger(SQLiteInterface.class.getName()).log(Level.SEVERE, null, ex);
                    break;
                }
              }
              result.close();
          }
        statement.close();
        return topTen;
      } catch (SQLException ex) {
        Logger.getLogger(SQLiteInterface.class.getName()).log(Level.SEVERE, null, ex);
        return null;
      }
    }
    
    public int[] getHead2Head(User user1, User user2){
        int wins[] = new int[2];
        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT winner FROM GameStats Where player_one IN (" + user1.getID() + ", " + user2.getID() + "), player_two IN (" +user1.getID() + ", " + user2.getID() + ");" );
            while(result.next()){
                int id = result.getInt("winner");
                if (id == user1.getID()){
                    wins[0]++;
                } else {
                    wins[1]++;
                }
            }
            return wins;
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteInterface.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public void createStats(int uid) {
        try{
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO UserStats (user_id , wins, losses, games) VALUES (" + uid + ", 0, 0, 0);");
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
