package osa.ora.server.utils;

// -----------------------------------------------------------------------------
// StringEncrypter.java
// -----------------------------------------------------------------------------

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import java.security.spec.KeySpec;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEParameterSpec;


public class StringEncrypter {
    public char[] keyUsed="FIM".toCharArray();
    Cipher ecipher;
    Cipher dcipher;
    //private static StringEncrypter stringEncrypter = null;

    
    /** 
     * Singleton
     */
    public static StringEncrypter getInstance(String initKey){
        return new StringEncrypter(initKey);
    }
    
    /**
     * private Constructor used to create this object.  Responsible for setting
     * and initializing this object's encrypter and decrypter Chipher instances
     * given a Pass Phrase and algorithm.
     * @param passPhrase Pass Phrase used to initialize both the encrypter and
     *                   decrypter instances.
     * 
     */
    private StringEncrypter(String initKey) {
        keyUsed=initKey.toCharArray();
        // 8-bytes Salt
        byte[] salt = {
            (byte)0xA9, (byte)0x9B, (byte)0xC8, (byte)0x32,
            (byte)0x56, (byte)0x34, (byte)0xE3, (byte)0x03
        };

        // Iteration count
        int iterationCount = 19;
        try {
            KeySpec keySpec = new PBEKeySpec(keyUsed, salt, iterationCount);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
            ecipher = Cipher.getInstance(key.getAlgorithm());
            dcipher = Cipher.getInstance(key.getAlgorithm());
            // Prepare the parameters to the cipthers
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        } catch (Exception e) {
            System.out.println("EXCEPTION: "+e.getMessage());
        }
    }


    /**
     * Takes a single String as an argument and returns an Encrypted version
     * of that String.
     * @param str String to be encrypted
     * @return <code>String</code> Encrypted version of the provided String
     */
    public String encrypt(String str) {
        if (str == null) return null;
        try {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes("UTF8");
            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);
            // Encode bytes to base64 to get a string
            return StringEncoder64.encode(enc);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Takes a encrypted String as an argument, decrypts and returns the
     * decrypted String.
     * @param str Encrypted String to be decrypted
     * @return <code>String</code> Decrypted version of the provided String
     */
    public String decrypt(String str) {
        if (str == null) return null;
        try {
            // Decode base64 to get bytes
            byte[] dec = StringEncoder64.decode(str);
            // Decrypt
            byte[] utf8 = dcipher.doFinal(dec);
            // Decode using utf-8
            return new String(utf8, "UTF8");
        } catch (Exception e) {
        }
        return null;
    }
}