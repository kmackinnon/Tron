/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import net.iharder.Base64;

/**
 * Implementation of the abstract Hash class, using the SHA-256 Algorithm (of the SHA-2 family)
 * It alternates between appending and prepending the salt,
 * over many iterations in order to increase difficulty in brute-forcing the password.
 * @author draringi
 */
public class SHA256Hash extends Hash {
  private static MessageDigest MSG_DIGEST;
  private static final int REPETITIONS = 1000;
  
  /**
   * Hashes password using the SHA-256 Algorithm, and a given salt.
   * Hashes through {@value #REPETITIONS } repetitions, alternating between appending and prepending the given salt.
   * @param password
   * @param salt
   * @return Hashed password
   */
  @Override
  public String hash(String password, String salt) throws NoSuchAlgorithmException{
    String tmp = password;
    int i;
    for(i=0;i<REPETITIONS;i++){
      if( (i % 2) == 0 ){ // A check to see if is an Even or Odd repition
        tmp = tmp + salt;
      } else {
        tmp = salt + tmp;
      }
      MSG_DIGEST.reset();
      MSG_DIGEST.update(tmp.getBytes());
      tmp = new String(MSG_DIGEST.digest());
    }
    return Base64.encodeBytes(tmp.getBytes());
  }
  
  public SHA256Hash(){
    try{
      MSG_DIGEST = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e){
      
    }
  }
}
