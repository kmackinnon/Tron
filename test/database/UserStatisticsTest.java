/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Contains unit tests for UserStatistics Class
 * @author Gabriel
 */
public class UserStatisticsTest {
    
    public UserStatistics statsTest;
    
    public UserStatisticsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        statsTest = new UserStatistics(0,0,0);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addWin method, of class UserStatistics.
     */
    @Test
    public void testAddWin() {
        System.out.println("addWin");
        UserStatistics instance = statsTest;
        instance.addWin();
    }

    /**
     * Test of addLoss method, of class UserStatistics.
     */
    @Test
    public void testAddLoss() {
        System.out.println("addLoss");
        UserStatistics instance = statsTest;
        instance.addLoss();
    }

    /**
     * Test of getWins method, of class UserStatistics.
     */
    @Test
    public void testGetWins() {
        System.out.println("getWins");
        UserStatistics instance = statsTest;
        int expResult = 0;
        int result = instance.getWins();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLosses method, of class UserStatistics.
     */
    @Test
    public void testGetLosses() {
        System.out.println("getLosses");
        UserStatistics instance = statsTest;
        int expResult = 0;
        int result = instance.getLosses();
        assertEquals(expResult, result);
    }

    /**
     * Test of getGames method, of class UserStatistics.
     */
    @Test
    public void testGetGames() {
        System.out.println("getGames");
        UserStatistics instance = statsTest;
        int expResult = 0;
        int result = instance.getGames();
        assertEquals(expResult, result);
    }
    
}
