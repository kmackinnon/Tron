package database;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test all database queries. This relates to login and statistics.
 *
 * @author Keith MacKinnon
 */
public class UserTest {
    
    User testUser;

    public UserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        testUser = new User(1, "Keith"); //created a random User with uid=1
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    
    @Test
    public void testGetUsername() {
        assertTrue(testUser.getUsername().equals("Keith")); 
        assertFalse(testUser.getUsername().equals("eith")); 
    }
   
}
