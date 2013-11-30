package database;

import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import net.iharder.Base64;

/**
 * Abstract Hash class that provides salt generation and contains an abstract
 * hash method.
 *
 * @author Michael Williams
 */
public abstract class Hash {

    static SecureRandom NUMBER_GENERATOR;

    public abstract String hash(String password, String salt) throws NoSuchAlgorithmException;

    /**
     * Generates a random salt for using in password hashing.
     *
     * @param length
     * @return Random Salt
     * @throws java.security.NoSuchAlgorithmException
     */
    public static String genSalt(int length) throws NoSuchAlgorithmException {
        if (NUMBER_GENERATOR == null) {
            NUMBER_GENERATOR = SecureRandom.getInstance("SHA1PRNG");
        }
        byte salt[] = NUMBER_GENERATOR.generateSeed(length);
        return Base64.encodeBytes(salt);
    }
}
