/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class StringToMd5 {
    StringToMd5() {
    }

    public static String stringToMd5(String text) {
        if (text.equals("")) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(text.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            Object hashText = number.toString(16);
            while (((String)hashText).length() < 32) {
                hashText = "0" + (String)hashText;
            }
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return "";
    }
}
