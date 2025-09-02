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
    
    @Test
        public void testNothing(){
            StringToMd5 s = new StringToMd5();
            }
    @Test
    public void testNormalString() {
    	String text = "Hello world";
    	assertEquals("65a8e27d8879283831b664bd8b7f0ad4", StringToMd5.stringToMd5(text));
    }
    @Test
    public void testEdgeCaseString() {
        String text = "A B C";
        assertEquals("0ef78513b0cb8cef12743f5aeb35f888", StringToMd5.stringToMd5(text));
    }
                                    
}