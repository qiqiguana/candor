package osa.ora.server.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.spec.KeySpec;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEParameterSpec;

public class StringEncrypter {

    /**
     * Takes a single String as an argument and returns an Encrypted version
     * of that String.
     *
     * @param str String to be encrypted
     * @return <code>String</code> Encrypted version of the provided String
     */
    public String encrypt(String str);
}
