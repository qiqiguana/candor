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
    
    @Test
        public void testNothing(){
            StringToMd5 s = new StringToMd5();
            }
    @Test
    public void testStringToMd5_with_valid_input() {
        // Given
        String text = "Hello world";
        String expected = "3e25960a79dbc69b674cd4ec67a72c62";
        // When
        String result = StringToMd5.stringToMd5(text);
        // Then
        assertEquals(expected, result);
    }
    @Test
    public void testMd5HashGenerationWithLeadingZeroes() {
        String text = "A B C";
        String expected = "0ef78513b0cb8cef12743f5aeb35f888";
        assertEquals(expected, StringToMd5.stringToMd5(text));
    }
                                    
}