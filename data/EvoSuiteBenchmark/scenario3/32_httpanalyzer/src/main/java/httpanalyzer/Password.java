package httpanalyzer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author David Scott
 */
public class Password {

    /**
     * Returns a String containing the encrypted passwd
     *
     * @param strpw A String containing the un-encrypted password
     * @param strsalt A 2 character String, containing the salt to
     * 				encrypt the password with.
     * @returns String containing encrypted password.
     */
    public static String crypt(String strpw, String strsalt);
}
