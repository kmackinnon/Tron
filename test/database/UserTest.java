package database;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test all methods of User. Ensure to delete existing databases!
 *
 * @author Keith MacKinnon
 */
public class UserTest {
    
    private static User testUser;
    private static DatabaseInterface db;

    public UserTest() {
    }

    /**
     * Create a new database interface at the beginning of the test.
     */
    @BeforeClass
    public static void setUpClass() {
        db = new SQLiteInterface(".testing.db");
    }

    @AfterClass
    public static void tearDownClass() {

    }

    /**
     * Create a new user before each method so that state (wins etc) is not saved)
     */
    @Before
    public void setUp() {
        testUser = new User(1, "Sankalp"); // creates a user with uid = 1
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createUser method, of class User.
     */
    @Test
    public void testCreateUser() {
        System.out.println("createUser");
        String username = "Nancy";
        String password = "Nancy93!";
        User expResult = db.addUser(username, password);
        User result = User.createUser(username, password);
        assertEquals(expResult, result);
    }

    /**
     * Test of changePassword method, of class User.
     */
    @Test
    public void testChangePassword() {
        System.out.println("changePassword");
        String password = "testUser93!";
        User instance = testUser;
        instance.changePassword(password);
    }

    /**
     * Test of login method, of class User.
     */
    @Test
    public void testLogin() {
        System.out.println("login");
        String password = "testUser93!";
        User instance = testUser;
        instance.login(password);
    }

    /**
     * Test of winGame method, of class User.
     */
    @Test
    public void testWinGame() {
        System.out.println("winGame");
        User instance = testUser;
        instance.winGame();
    }

    /**
     * Test of loseGame method, of class User.
     */
    @Test
    public void testLoseGame() {
        System.out.println("loseGame");
        User instance = testUser;
        instance.loseGame();
    }

    /**
     * Test of getID method, of class User.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        User instance = testUser;
        int expResult = 1;
        int result = instance.getID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStats method, of class User.
     */
    @Test
    public void testGetStats() {
        System.out.println("getStats");
        User instance = testUser;
        UserStatistics expResult = db.getUserStats(1);
        UserStatistics result = instance.getStats();
        assertEquals(expResult, result);
    }

    /**
     * Test of isAuthenticated method, of class User.
     */
    @Test
    public void testIsAuthenticated() {
        System.out.println("isAuthenticated");
        User instance = testUser;
        boolean expResult = false; // haven't actually authenticated user
        boolean result = instance.isAuthenticated();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTopTen method, of class User.
     */
    @Test
    public void testGetTopTen() {
        System.out.println("getTopTen");
        String[][] expResult = new String[2][10];
        expResult[0][0] = "Nancy";
        expResult[1][0] = "0";
        String[][] result = User.getTopTen();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getGamesWon method, of class User.
     */
    @Test
    public void testGetGamesWon() {
        System.out.println("getGamesWon");
        User instance = testUser;
        int expResult = 0; //since user gets overwritten after each method
        int result = instance.getGamesWon();
        assertEquals(expResult, result);
    }

    /**
     * Test of getGamesPlayed method, of class User.
     */
    @Test
    public void testGetGamesPlayed() {
        System.out.println("getGamesPlayed");
        User instance = testUser;
        int expResult = 0;
        int result = instance.getGamesPlayed();
        assertEquals(expResult, result);
    }

    /**
     * Test of getGamesLost method, of class User.
     */
    @Test
    public void testGetGamesLost() {
        System.out.println("getGamesLost");
        User instance = testUser;
        int expResult = 0;
        int result = instance.getGamesLost();
        assertEquals(expResult, result);
    }

    /**
     * Test of saveStats method, of class User.
     */
    @Test
    public void testSaveStats() {
        System.out.println("saveStats");
        User instance = testUser;
        instance.saveStats();
    }

    /**
     * Test of getHead2HeadStats method, of class User.
     */
    @Test
    public void testGetHead2HeadStats() {
        System.out.println("getHead2HeadStats");
        User user1 = testUser;
        User user2 = db.getUser("Nancy");
        int[] expResult = {0,0};
        int[] result = User.getHead2HeadStats(user1, user2);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getUser method, of class User.
     */
    @Test
    public void testGetUser() {
        System.out.println("getUser");
        String username = "Sankalp";
        String password = null;
        User expResult = db.getUser(username);
        User result = User.getUser(username, password);
        assertEquals(expResult, result);
    }

    /**
     * Test of getUsername method, of class User.
     */
    @Test
    public void testGetUsername() {
        System.out.println("getUsername");
        User instance = testUser;
        String expResult = "Sankalp";
        String result = instance.getUsername();
        assertEquals(expResult, result);
        assertNotEquals("Keith", result);
    }
   
}
