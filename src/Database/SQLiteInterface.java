package Database;

import java.io.IOException;
import java.sql.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import java.util.List;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLiteInterface extends DatabaseInterface {

    private  Connection connection;
    private Statement statement;
    private final Hash hash;

    public SQLiteInterface(String db) {
        connection = null;
        supportList = new ArrayList();
        hash = new SHA256Hash();
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + db);
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Users'");
            if (!result.first()) { //If the Users table doesn't exist
                List<String> sqlCommands =
                        Files.readAllLines(Paths.get("/data/db_init.sql"),
                        Charset.defaultCharset());
                Iterator<String> it = sqlCommands.iterator();
                String sql;
                while (it.hasNext()) {
                    sql = it.next();
                    statement.execute(sql);
                }
                statement.close();
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            // Bad things... Very Bad Things...
        }
        supportList.add("user");
    }

    @Override
    public int getUser(String username) {
        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select * from Users where username = " + username + ";");
            if (result.first()) {
                int id = result.getInt("id");
                result.close();
                statement.close();
                return id;
                
            } else {
                result.close();
                statement.close();
                return -1;
            }
        } catch (SQLException e) {
            return -1; //TODO: replace this with proper error handling...
        }
    }

    
    @Override
    public boolean userExsits(String username) {
        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select * from Users where username = " + username + ";");
            if (result.first()) {
                int id = result.getInt("id");
                result.close();
                statement.close();
                return true;
            } else {
                result.close();
                statement.close();
                return false;
            }
        } catch (SQLException e) {
            return false; //TODO: replace this with proper error handling...
        }
    }

    @Override
    public boolean confirmUser(int uid, String password) {
        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select * from Users where uid = " + uid + ";");
            if (result.first()) {
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
        } catch (SQLException| NoSuchAlgorithmException e) {
            return false;
        }
    }

    @Override
    public void updateUser(int uid, UserStatistics stats) throws UnsupportedOperationException {
        //TODO: Implement this
    }

    @Override
    public int addUser(String username, String password) {
        try {
            statement = connection.createStatement();
            String salt = Hash.genSalt(20);
            String hashedPassword = hash.hash(password, salt);
            statement.executeUpdate("insert into Users (username,password,salt) values ('" + username + "', '" + hashedPassword + "', '" + salt + "');");
            statement.close();
            return getUser(username);
        } catch (SQLException | NoSuchAlgorithmException e) {
            return -1;
        }
    }

    @Override
    public void addGame(GameInfo game) throws UnsupportedOperationException {
        //TODO: Implement this
    }

    @Override
    public UserStatistics getUserStats(int uid) throws UnsupportedOperationException {
        return null;//TODO: Implement this
    }

    @Override
    public void changePassword(int uid, String newPassword) {
        try {
          String salt = Hash.genSalt(20);
          String hashedPassword = hash.hash(newPassword, salt);
          statement = connection.createStatement();
          statement.executeUpdate("update Users set password = '" + hashedPassword + "', salt = '" + salt + "' where id = " + uid + ";");
        } catch (SQLException | NoSuchAlgorithmException e) {
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
