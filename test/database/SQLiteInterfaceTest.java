package database;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;

/**
 *
 * @author Michael Williams
 */
public class SQLiteInterfaceTest {

    SQLiteInterface db;

    public SQLiteInterfaceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        db = new SQLiteInterface(".test.db");
    }

    @After
    public void tearDown() {
        File f = new File(".test.db");
        f.delete();
    }

    /**
     * Test of getUser method, of class SQLiteInterface.
     */
    @Test
    public void testGetUser() {
        System.out.println("getUser");
        String username = "bob";
        User expResult = null;
        User result = db.getUser(username);
        assertEquals(expResult, result);
    }

    /**
     * Test of userExists method, of class SQLiteInterface.
     */
    @Test
    public void testUserExists() {
        System.out.println("userExists");
        String username = "bob";
        boolean expResult = false;
        boolean result = db.userExists(username);
        assertEquals(expResult, result);
    }

    /**
     * Test of confirmUser method, of class SQLiteInterface.
     */
    @Test
    public void testConfirmUser() {
        System.out.println("confirmUser");
        int uid = 1;
        String password = "password";
        boolean expResult = false;
        boolean result = db.confirmUser(uid, password);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateUser method, of class SQLiteInterface.
     */
    @Test
    public void testUpdateUser() {
        System.out.println("updateUser");
        int uid = 1;
        UserStatistics stats = new UserStatistics(2, 2, 4);
        db.updateUser(uid, stats);
    }

    /**
     * Test of addUser method, of class SQLiteInterface.
     */
    @Test
    public void testAddUser() {
        System.out.println("addUser");
        String username = "user";
        String password = "Pass00!!";
        User expResult = new User(1, "user");
        User result = db.addUser(username, password);
        assertEquals(expResult, result);
        User dbReturn = db.getUser(username);
        assertEquals(dbReturn, result);
    }

    /**
     * Test of addGame method, of class SQLiteInterface.
     */
    @Test
    public void testAddGame() {
        System.out.println("addGame");
        GameInfo game = null;
        db.addGame(game);
    }

    /**
     * Test of getUserStats method, of class SQLiteInterface.
     */
    @Test
    public void testGetUserStats() {
        System.out.println("getUserStats");
        int uid = 0;
        SQLiteInterface instance = null;
        UserStatistics expResult = null;
        UserStatistics result = db.getUserStats(uid);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changePassword method, of class SQLiteInterface.
     */
    @Test
    public void testChangePassword() {
        System.out.println("changePassword");
        int uid = 1;
        String newPassword = "NewPassword!";
        db.changePassword(uid, newPassword);
        boolean result = true;
        boolean expResult = db.confirmUser(uid, newPassword);
        assertEquals(expResult, result);
    }

    /**
     * Test of addMap method, of class SQLiteInterface.
     */
    @Test
    public void testAddMap() {
        System.out.println("addMap");
        String name = "testmap";
        byte[] data = null;
        int width = 20;
        int height = 25;
        db.addMap(name, data, width, height);
        DatabaseInterface.MapSpecs result = db.getMap(name);
        assertEquals(height, result.height);
        assertEquals(width, result.width);
        assertArrayEquals(data, result.data);
    }

    /**
     * Test of getMap method, of class SQLiteInterface.
     */
    @Test
    public void testGetMap() {
        System.out.println("getMap");
        String name = "BasicMap1";
        int expWidth = 75;
        int expHeight = 50;
        byte expData[] = null;
        DatabaseInterface.MapSpecs result = db.getMap(name);
        assertEquals(expWidth, result.width);
        assertEquals(expHeight, result.height);
        assertArrayEquals(expData, result.data);
    }

    /**
     * Test of getTopTenPlayerStats method, of class SQLiteInterface.
     */
    @Test
    public void testGetTopTenPlayerStats() {
        System.out.println("getTopTenPlayerStats");
        String[][] expResult = new String[2][10];
        expResult[0][0] = "bob";
        expResult[1][0] = String.valueOf(0);
        String[][] result = db.getTopTenPlayerStats();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getHead2Head method, of class SQLiteInterface.
     */
    @Test
    public void testGetHead2Head() {
        System.out.println("getHead2Head");
        User user1 = new User(1, "bob");
        User user2 = new User(1, "bob");
        int[] expResult = new int[2];
        expResult[0] = expResult[1] = 0;
        int[] result = db.getHead2Head(user1, user2);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of createStats method, of class SQLiteInterface.
     */
    @Test
    public void testCreateStats() {
        System.out.println("createStats");
        int uid = 1;
        db.createStats(uid);
        UserStatistics expResult = new UserStatistics(0, 0, 0);
        UserStatistics result = db.getUserStats(uid);
        assertEquals(expResult, result);
    }

}
