/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameplay;

import database.User;
import database.GameInfo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Contains unit tests for Player class
 * @author Gabriel
 */
public class PlayerTest {
    
    public static User testUser1;
    public static User testUser2;
    //GameInfo testGame;
    public PlayerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        testUser1 = new User(1, "Gabriel");
        
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

    /**
     * Test of getID method, of class Player.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        Player instance = new Player(testUser1, null, null);
        int expResult = 1;
        int result = instance.getID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getX method, of class Player.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        Player instance = new Player(0, 0, null, null, null, null, null);
        int expResult = 0;
        int result = instance.getX();
        assertEquals(expResult, result);
    }

    /**
     * Test of getY method, of class Player.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        Player instance = new Player(0, 0, null, null, null, null, null);
        int expResult = 0;
        int result = instance.getY();
        assertEquals(expResult, result);
    }

    /**
     * Test of getColour method, of class Player.
     */
    @Test
    public void testGetColour() {
        System.out.println("getColour");
        Player instance = new Player(0, 0, "0x00F", null, null, null, null);
        String expResult = "0x00F";
        String result = instance.getColour();
        assertEquals(expResult, result);
    }

    /**
     * Test of getIsAlive method, of class Player.
     */
    @Test
    public void testGetIsAlive() {
        System.out.println("getIsAlive");
        Player instance = new Player(testUser1, null, null);
        instance.setIsAlive(true);
        boolean expResult = true;
        boolean result = instance.getIsAlive();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUsername method, of class Player.
     */
    @Test
    public void testGetUsername() {
        System.out.println("getUsername");
        Player instance = new Player(testUser1, null, null);
        String expResult = "Gabriel";
        String result = instance.getUsername();
        assertEquals(expResult, result);
        expResult = "Gabe";
        assertNotEquals(expResult, result);
    }

    /**
     * Test of setX method, of class Player.
     */
    @Test
    public void testSetX() {
        System.out.println("setX");
        int x = 10;
        Player instance = new Player(null, null, null);
        instance.setX(x);
    }

    /**
     * Test of setY method, of class Player.
     */
    @Test
    public void testSetY() {
        System.out.println("setY");
        int y = 25;
        Player instance = new Player(null, null, null);
        instance.setY(y);
    }

    /**
     * Test of setIsAlive method, of class Player.
     */
    @Test
    public void testSetIsAlive() {
        System.out.println("setIsAlive");
        boolean isAlive = false;
        Player instance = new Player(null, null, null);
        instance.setIsAlive(isAlive);
        isAlive = true;
        instance.setIsAlive(isAlive);
    }

    /**
     * Test of init method, of class Player.
     */
    @Test
    public void testInit() {
        System.out.println("init");
        int xStart = 0;
        int yStart = 0;
        Player.Direction startingDirection = Player.Direction.UP;
        Map map = null;
        Player instance = new Player(testUser1, null, null);
        instance.init(xStart, yStart, startingDirection, map);
    }

    /**
     * Test of moveUp method, of class Player.
     */
    @Test
    public void testMoveUp() {
        System.out.println("moveUp");
        Player instance = new Player(10, 10, null, null, null, Player.Direction.LEFT, null);
        instance.moveUp();
    }

    /**
     * Test of moveDown method, of class Player.
     */
    @Test
    public void testMoveDown() {
        System.out.println("moveDown");
        Player instance = new Player(10, 10, null, null, null, Player.Direction.LEFT, null);
        instance.moveDown();
    }

    /**
     * Test of moveRight method, of class Player.
     */
    @Test
    public void testMoveRight() {
        System.out.println("moveRight");
        Player instance = new Player(10, 10, null, null, null, Player.Direction.UP, null);
        instance.moveRight();
    }

    /**
     * Test of moveLeft method, of class Player.
     */
    @Test
    public void testMoveLeft() {
        System.out.println("moveLeft");
        Player instance = new Player(10, 10, null, null, null, Player.Direction.UP, null);
        instance.moveLeft();
    }

    /**
     * Test of moveCurrent method, of class Player.
     */
    @Test
    public void testMoveCurrent() {
        System.out.println("moveCurrent");
        Map testMap = new Map(0,0,null,null);
        Player instance =  new Player(10, 10, "0x00F", null, testMap, Player.Direction.LEFT, null);
        instance.moveCurrent();
    }

    /**
     * Test of winRound method, of class Player.
     */
    @Test
    public void testWinRound() {
        System.out.println("winRound");
        Player instance = new Player(testUser1, null, null);
        instance.winRound();
    }

    /**
     * Test of getNumRoundsWon method, of class Player.
     */
    @Test
    public void testGetNumRoundsWon() {
        System.out.println("getNumRoundsWon");
        Player instance = new Player(testUser1, null, null);
        int expResult = 0;
        int result = instance.getNumRoundsWon();
        assertEquals(expResult, result);
    }

    /**
     * Test of winGame method, of class Player.
     */
    @Test
    public void testWinGame() {
        System.out.println("winGame");
        Player instance = new Player(testUser1, null, null);
        instance.winGame();
    }

    /**
     * Test of loseGame method, of class Player.
     */
    @Test
    public void testLoseGame() {
        System.out.println("loseGame");
        Player instance = new Player(testUser1, null, null);
        instance.loseGame();
    }

    /**
     * Test of saveStats method, of class Player.
     */
    @Test
    public void testSaveStats() {
        System.out.println("saveStats");
        Player instance = new Player(testUser1, null, null);
        instance.saveStats();
    }

    /**
     * Test of getUser method, of class Player.
     */
    @Test
    public void testGetUser() {
        System.out.println("getUser");
        Player instance = new Player(testUser1, null, null);
        User expResult = testUser1;
        User result = instance.getUser();
        assertEquals(expResult, result);
    }
    
}
