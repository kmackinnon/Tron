package Database;

import java.sql.*;

public class SQLiteInterface extends DatabaseInterface {
  Connection connection;

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
