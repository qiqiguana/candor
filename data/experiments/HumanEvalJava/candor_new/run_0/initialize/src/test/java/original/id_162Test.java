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
    void testStringToMd5_WhenGivenAnEmptyString_ReturnsNull() {
        // Given
        String text = "";
        
        // When
        String result = StringToMd5.stringToMd5(text);
        
        // Then
        assertNull(result);
    }
}