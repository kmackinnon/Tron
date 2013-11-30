/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameplay;

import javafx.scene.input.KeyEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Used for unit testing of the Controller class
 *
 * @author Gabriel
 */
public class ControllerTest {

    public ControllerTest() {
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
     * Test of getInstance method, of class Controller.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        Controller expResult = null;
        Controller result = Controller.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clear method, of class Controller.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        Controller instance = null;
        instance.clear();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addBinding method, of class Controller.
     */
    @Test
    public void testAddBinding() {
        System.out.println("addBinding");
        Input input = null;
        Controller instance = null;
        instance.addBinding(input);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of handle method, of class Controller.
     */
    @Test
    public void testHandle() {
        System.out.println("handle");
        KeyEvent e = null;
        Controller instance = null;
        instance.handle(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
