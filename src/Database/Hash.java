/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
/**
 * Abstract Hash class that provides salt generation and contains an abstract hash method.
 * @author draringi
 */
public abstract class Hash {
  static SecureRandom NUMBER_GENERATOR;
  public abstract String hash(String password, String salt);
  
  public Hash(){
    if (NUMBER_GENERATOR==null){
      try{
        NUMBER_GENERATOR=SecureRandom.getInstance("SHA1PRNG");
      } catch (NoSuchAlgorithmException e) {
        
      }
    }
  }
  /**
   * Generates a random salt for using in password hashing.
   * 
   * @param length
   * @return Random Salt
   */
  public static String genSalt(int length){
    byte salt[] = NUMBER_GENERATOR.generateSeed(length);
    return new String(salt);
  }
}
