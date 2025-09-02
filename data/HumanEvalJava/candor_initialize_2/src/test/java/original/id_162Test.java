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
    void testStringToMd5_ReturnsNull_WhenInputIsEmpty() {
        // Arrange
        String input = "";
        // Act
        String result = StringToMd5.stringToMd5(input);
        // Assert
        assertNull(result);
    }
    
    @Test
        public void testNothing(){
            StringToMd5 s = new StringToMd5();
            }
    @Test
    void testEmptyStringReturnsNull() {
        // Given an empty string
        String text = "";
        
        // When calling stringToMd5
        String result = StringToMd5.stringToMd5(text);
        
        // Then the result should be null
        assertNull(result);
    }
    @Test
    void testMd5HashGeneration() {
        // Given a non-empty string
        String text = "Hello world";
        
        // When calling stringToMd5
        String result = StringToMd5.stringToMd5(text);
        
        // Then the result should be the expected MD5 hash
        assertEquals("3e25960a79dbc69b674cd4ec67a72c62", result);
    }
    @Test
    void testMessageDigestGetInstance() {
        // Given a non-empty string
        String text = "Hello world";
        
        try {
            // When calling MessageDigest.getInstance
            MessageDigest md = MessageDigest.getInstance("MD5");
            
            // Then the instance should be an MD5 instance
            assertNotNull(md);
        } catch (NoSuchAlgorithmException e) {
            fail("Unexpected NoSuchAlgorithmException");
        }
    }
    @Test
    void testDigestAndHashGeneration() {
        // Given a non-empty string
        String text = "Hello world";
        
        try {
            // When calling MessageDigest.getInstance and digest
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(text.getBytes());
            
            // Then the digest should be a non-empty array
            assertNotNull(messageDigest);
            assertTrue(messageDigest.length > 0);
        } catch (NoSuchAlgorithmException e) {
            fail("Unexpected NoSuchAlgorithmException");
        }
    }
    @Test
    void testMessageDigestGetInstanceReturnsMD5Instance() {
        // Given a non-empty string
        String text = "Hello world";
        
        try {
            // When calling MessageDigest.getInstance
            MessageDigest md = MessageDigest.getInstance("MD5");
            
            // Then the instance should be an MD5 instance
            assertEquals("MD5", md.getAlgorithm());
        } catch (NoSuchAlgorithmException e) {
            fail("Unexpected NoSuchAlgorithmException");
        }
    }
    @Test
    void testStringToMd5ThrowsNullPointerExceptionWhenInputIsNull() {
        // Given a null string
        String text = null;
        
        try {
            // When calling stringToMd5
            StringToMd5.stringToMd5(text);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // Then the exception should be thrown
        }
    }
    @Test
    public void testMd5HashWithPadding() {
        String text = "a";
        String expectedHash = "0cc175b9c0f1b6a831c399e269772661";
        assertEquals(expectedHash, StringToMd5.stringToMd5(text));
    }
    @Test
    public void testMd5HashWithNoPadding_1() {
        String text = "";
        assertNull(StringToMd5.stringToMd5(text));
    }
                                    
}