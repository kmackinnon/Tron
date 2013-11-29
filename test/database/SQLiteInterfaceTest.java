package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Keith
 */
public class SQLiteInterfaceTest {
    
    private static Connection connection;
    private static Statement statement;
    
    public SQLiteInterfaceTest() {
    }
    
    /**
     * Initiate a connection with the database to get data.
     */
    
    @BeforeClass
    public static void setUpClass() {
        String db = ".lightracer.db";
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

            // create a sql statement
            try {
                statement = connection.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(SQLiteInterface.class.getName()).log(Level.SEVERE, null, ex);
            }

            // check for the Users table
            try {
                statement.executeQuery("SELECT * FROM Users;");
                statement.close();
            } catch (SQLException e) {
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Something bad happened in init" + e.getMessage());
        }
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
