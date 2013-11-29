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
 * Contains unit tests for MapGenerator Class
 *
 * @author Gabriel
 */
public class MapGeneratorTest {

    public MapGeneratorTest() {
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
     * Test of makeMap1 method, of class MapGenerator.
     */
    @Test
    public void testMakeMap1() {
        System.out.println("makeMap1");
        MapGenerator instance = new MapGenerator();
        instance.makeMap1();
    }

    /**
     * Test of saveMap1 method, of class MapGenerator.
     */
    @Test
    public void testSaveMap1() {
        System.out.println("saveMap1");
        MapGenerator instance = new MapGenerator();
        instance.makeMap1();
        instance.saveMap1();
    }

    /**
     * Test of makeMap2 method, of class MapGenerator.
     */
    @Test
    public void testMakeMap2() {
        System.out.println("makeMap2");
        MapGenerator instance = new MapGenerator();
        instance.makeMap2();
    }

    /**
     * Test of saveMap2 method, of class MapGenerator.
     */
    @Test
    public void testSaveMap2() {
        System.out.println("saveMap2");
        MapGenerator instance = new MapGenerator();
        instance.makeMap2();
        instance.saveMap2();
    }

    /**
     * Test of makeMap3 method, of class MapGenerator.
     */
    @Test
    public void testMakeMap3() {
        System.out.println("makeMap3");
        MapGenerator instance = new MapGenerator();
        instance.makeMap3();
    }

    /**
     * Test of saveMap3 method, of class MapGenerator.
     */
    @Test
    public void testSaveMap3() {
        System.out.println("saveMap3");
        MapGenerator instance = new MapGenerator();
        instance.makeMap3();
        instance.saveMap3();
    }

    /**
     * Test of makeAllAndInstall method, of class MapGenerator.
     */
    @Test
    public void testMakeAllAndInstall() {
        System.out.println("makeAllAndInstall");
        MapGenerator instance = new MapGenerator();
        instance.makeAllAndInstall();
    }

}
