/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

import database.SHA256Hash;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author draringi
 */
public class SHA256HashTest {
  
  public SHA256HashTest() {
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
   * Test of hash method, of class SHA256Hash.
   */
  @Test
  public void testHash() throws Exception {
    System.out.println("hash");
    String password = "password";
    String salt = "";
    SHA256Hash instance = new SHA256Hash();
    String expResult = "G++/vWHvv71uNmIb77+9Z++/ve+/ve+/vRTvv73vv70MNlnvv71v77+977+9M++/ve+/ve+/ve+/vUvvv73vv70=";
    String result = instance.hash(password, salt);
    assertEquals(expResult, result);
    String empty = "";
    expResult = "77+977+977+9Qirvv73vv73vv71R77+9WO+/ve+/ve+/vVPvv73vv70j77+9YAbvv71c77+977+977+977+9Fu+/ve+/vRdH";
    result = instance.hash(empty, empty);
    assertEquals(expResult, result);
  }
  
}
