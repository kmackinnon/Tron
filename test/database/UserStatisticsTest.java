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
 *
 * @author Michael Williams
 */
public class UserStatisticsTest {

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
        UserStatistics instance = new UserStatistics(0, 0, 0);
        instance.addWin();
        assertEquals(instance.getWins(), 1);
        assertEquals(instance.getWins(), instance.getGames());
    }

    /**
     * Test of addLoss method, of class UserStatistics.
     */
    @Test
    public void testAddLoss() {
        System.out.println("addLoss");
        UserStatistics instance = new UserStatistics(0, 0, 0);
        instance.addLoss();
        assertEquals(instance.getLosses(), 1);
        assertEquals(instance.getLosses(), instance.getGames());
    }

    /**
     * Test of getWins method, of class UserStatistics.
     */
    @Test
    public void testGetWins() {
        System.out.println("getWins");
        UserStatistics instance = new UserStatistics(3, 1, 4);
        int expResult = 3;
        int result = instance.getWins();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLosses method, of class UserStatistics.
     */
    @Test
    public void testGetLosses() {
        System.out.println("getLosses");
        UserStatistics instance = new UserStatistics(3, 1, 4);
        int expResult = 1;
        int result = instance.getLosses();
        assertEquals(expResult, result);
    }

    /**
     * Test of getGames method, of class UserStatistics.
     */
    @Test
    public void testGetGames() {
        System.out.println("getGames");
        UserStatistics instance = new UserStatistics(3, 1, 4);
        int expResult = 4;
        int result = instance.getGames();
        assertEquals(expResult, result);
        assertEquals(instance.getLosses() + instance.getWins(), result);
    }
}
