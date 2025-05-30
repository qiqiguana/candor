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
    void testStringToMd5ForEmptyString() {
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
    public void testStringToMd5WithEmptyString() {
        String input = "";
        assertNull(StringToMd5.stringToMd5(input));
    }
    @Test
    public void testStringToMd5WithValidInput() {
        String input = "Hello world";
        assertEquals("3e25960a79dbc69b674cd4ec67a72c62", StringToMd5.stringToMd5(input));
    }
    @Test
    public void testStringToMd51() {
        String input = "Hello world";
        assertEquals("3e25960a79dbc69b674cd4ec67a72c62", StringToMd5.stringToMd5(input));
    }
    @Test
    public void testStringToMd5WithNullInput() {
        String input = null;
        try {
            StringToMd5.stringToMd5(input);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
    @Test
    public void testStringToMd5WithShortInput() {
        String input = "a";
        assertEquals("0cc175b9c0f1b6a831c399e269772661", StringToMd5.stringToMd5(input));
    }
    @Test
    public void testStringToMd5_NonEmptyString() {
        // Arrange
        String text = "Hello world";
        String expectedHash = "3e25960a79dbc69b674cd4ec67a72c62";
        
        // Act
        String actualHash = StringToMd5.stringToMd5(text);
        
        // Assert
        assertEquals(expectedHash, actualHash);
    }
    @Test
    public void testStringToMd5_EmptyString() {
        // Arrange
        String text = "";
        
        // Act
        String actualHash = StringToMd5.stringToMd5(text);
        
        // Assert
        assertNull(actualHash);
    }
    @Test
        public void testStringToMd5_NoSuchAlgorithmException() {
          // Arrange
          String text = "Hello world";
          
          try {
            // Act and Assert
            MessageDigest md = MessageDigest.getInstance("InvalidAlgorithm");
            md.digest(text.getBytes());
            fail("Expected NoSuchAlgorithmException to be thrown.");
          } catch (NoSuchAlgorithmException e) {
            assertInstanceOf(NoSuchAlgorithmException.class, e);
          }
        }
                                    
}