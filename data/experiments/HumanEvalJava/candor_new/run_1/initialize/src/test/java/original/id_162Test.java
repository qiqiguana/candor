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
    void testEmptyString() {
        // Arrange and Act
        String result = StringToMd5.stringToMd5("");

        // Assert
        assertNull(result);
    }
}
