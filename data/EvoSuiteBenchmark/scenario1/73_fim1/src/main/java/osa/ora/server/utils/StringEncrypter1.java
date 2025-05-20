package osa.ora.server.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.spec.KeySpec;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEParameterSpec;

public class StringEncrypter {

    public String decrypt(String str) {
        if (str == null)
            return null;
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
