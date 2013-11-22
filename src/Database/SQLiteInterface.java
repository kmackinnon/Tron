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
    }

    @Override
    public int getUser(String username) {
        try {
          statement = connection.createStatement();
          username = Base64.encodeBytes(username.getBytes());
          int id;
          try (ResultSet result = statement.executeQuery("select id from Users where username = '" + username + "';")) {
            id = result.getInt("id");
          }
          statement.close();
          return id;
        } catch (SQLException e) {
          return -1; //TODO: replace this with proper error handling...
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
        } catch (SQLException e) {
        }
    }

    @Override
    public int addUser(String username, String password) {
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
                return -1;
            }
            statement.executeUpdate("insert into Users (username,password,salt) values ('" + username + "', '" + hashedPassword + "', '" + salt + "');");
            statement.close();
            return getUser(realUsername);
        } catch (SQLException  e) {
            System.out.println("Something bad happened in creation" + e.getMessage());
            return -1;
        }
    }

    @Override
    public void addGame(GameInfo game) throws UnsupportedOperationException {
        try {
          statement = connection.createStatement();
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
        } catch (SQLException | NoSuchAlgorithmException e) {
            System.out.println("Something bad happened");
        }
    }

    @Override
    public void addMap(String name, String mapString) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getMap(String name) throws UnsupportedOperationException {
        return null; //TODO: have this throw an implementation error if not implemented
    }
}
