package httpanalyzer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author David Scott
 */
public class Password {

    public static String crypt(String strpw, String strsalt) {
        char[] pw = strpw.toCharArray();
        char[] salt = strsalt.toCharArray();
        byte[] pwb = new byte[66];
        char[] result = new char[13];
        byte[] new_etr = new byte[etr.length];
        int n = 0;
        int m = 0;
        while (m < pw.length && n < 64) {
            for (int j = 6; j >= 0; j--) {
                pwb[n++] = (byte) ((pw[m] >> j) & 1);
            }
            // Increment pw
            m++;
            pwb[n++] = 0;
        }
        while (n < 64) {
            pwb[n++] = 0;
        }
        definekey(pwb);
        for (n = 0; n < 66; n++) {
            pwb[n] = 0;
        }
        System.arraycopy(etr, 0, new_etr, 0, new_etr.length);
        EP = new_etr;
        for (int i = 0; i < 2; i++) {
            char c = salt[i];
            result[i] = c;
            if (c > 'Z') {
                // c was a lowercase letter
                c -= 6 + 7 + '.';
            } else if (c > '9') {
                // c was a uppercase letter
                c -= 7 + '.';
            } else {
                // c was a digit, '.' or '/'
                c -= '.';
            }
            // now, 0 <= c <= 63
            for (int j = 0; j < 6; j++) {
                if (((c >> j) & 1) == 1) {
                    byte t = (byte) (6 * i + j);
                    byte temp = new_etr[t];
                    new_etr[t] = new_etr[t + 24];
                    new_etr[t + 24] = temp;
                }
            }
        }
        if (result[1] == 0) {
            result[1] = result[0];
        }
        for (int i = 0; i < 25; i++) {
            encrypt(pwb, 0);
        }
        EP = etr;
        m = 2;
        n = 0;
        while (n < 66) {
            int c = 0;
            for (int j = 6; j > 0; j--) {
                c <<= 1;
                c |= pwb[n++];
            }
            // becomes >= '.'
            c += '.';
            if (c > '9') {
                // not in [./0-9], becomes upper
                c += 7;
            }
            if (c > 'Z') {
                // not in [A-Z], becomes lower
                c += 6;
            }
            result[m++] = (char) c;
        }
        return (new String(result));
    }
}
