package original;

import java.math.BigInteger;

import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StringToMd5.
*/
class StringToMd5Test {
    @Test
    void testStringToMd5WithEmptyString() {
        assertNull(StringToMd5.stringToMd5(""));
    }
}