/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameplay;

import database.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Contains unit tests for Map class
 * @author Gabriel
 */
public class MapTest {
    
    public MapTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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
     * Test of addWall method, of class Map.
     */
    @Test
    public void testAddWall() {
        System.out.println("addWall");
        int xPos = 0;
        int yPos = 0;
        String colour = "0x00F";
        Map instance = new Map(null, null,null,null);
        instance.addWall(xPos, yPos, colour);
    }

    /**
     * Test of getWidth method, of class Map.
     */
    @Test
    public void testGetWidth() {
        System.out.println("getWidth");
        Map instance = new Map(0,0,null,null);
        int expResult = 0;
        int result = instance.getWidth();
        assertEquals(expResult, result);
    }

    /**
     * Test of getHeight method, of class Map.
     */
    @Test
    public void testGetHeight() {
        System.out.println("getHeight");
        Map instance = new Map(0,0,null,null);
        int expResult = 0;
        int result = instance.getHeight();
        assertEquals(expResult, result);
    }

    /**
     * Test of setSpeed method, of class Map.
     */
    @Test
    public void testSetSpeed() {
        System.out.println("setSpeed");
        int hz = 10;
        Map instance = new Map(0,0,null,null);
        instance.setSpeed(hz);
    }

    /**
     * Test of collides method, of class Map.
     */
    @Test
    public void testCollides() {
        System.out.println("collides");
        int x = 0;
        int y = 0;
        Map instance = new Map(10,10,null,null);
        boolean expResult = false;
        boolean result = instance.collides(x, y);
        assertEquals(expResult, result);
    }

    /**
     * Test of run method, of class Map.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        Map instance = null;
        instance.run();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlayerWins method, of class Map.
     */
    @Test
    public void testGetPlayerWins() {
        System.out.println("getPlayerWins");
        int index = 0;
        Map instance = new Map(10,10,null,null);
        int expResult = 0;
        int result = instance.getPlayerWins(index);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlayer method, of class Map.
     */
    @Test
    public void testGetPlayer() {
        System.out.println("getPlayer");
        int i = 0;
        Map instance = null;
        Player expResult = null;
        Player result = instance.getPlayer(i);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPlayer method, of class Map.
     */
    @Test
    public void testAddPlayer_5args() {
        System.out.println("addPlayer (user, colour, direction, x, y)");
        User user = new User(1, "Gabriel");
        String colour = "0x00F";
        Player.Direction direction = Player.Direction.UP;
        int x = 0;
        int y = 0;
        Map instance = new Map(10,10,null,null);
        instance.addPlayer(user, colour, direction, x, y);
    }

    /**
     * Test of addPlayer method, of class Map.
     */
    @Test
    public void testAddPlayer_4args() {
        System.out.println("addPlayer (player, direction, x, y)");
        Player player = new Player(null,null,null);
        Player.Direction direction = Player.Direction.UP;
        int x = 0;
        int y = 0;
        Map instance = new Map(10,10,null,null);
        instance.addPlayer(player, direction, x, y);
    }

    /**
     * Test of displayPlayer method, of class Map.
     */
    @Test
    public void testDisplayPlayer() {
        System.out.println("displayPlayer");
        int x = 0;
        int y = 0;
        String colour = "";
        Map instance = null;
        instance.displayPlayer(x, y, colour);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBinaryData method, of class Map.
     */
    @Test
    public void testGetBinaryData() {
        System.out.println("getBinaryData");
        Map instance = null;
        byte[] expResult = null;
        byte[] result = instance.getBinaryData();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
