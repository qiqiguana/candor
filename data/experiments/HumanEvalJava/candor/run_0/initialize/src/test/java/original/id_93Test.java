package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Encode.
*/
class EncodeTest {
    @Test
    void testEncode_SimpleMessage_EncodedCorrectly() {
        // Arrange
        String message = "test";
        String expected = "TGST";
        
        // Act
        String actual = Encode.encode(message);
        
        // Assert
        assertEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            Encode s = new Encode();
            }
    @Test
    public void testEncode_EmptyInput() {
    	String result = Encode.encode("");
    	assertEquals("", result);
    }
    @Test
    public void testEncodeMessageWithOnlyUppercaseLetters2() {
        String input = "WORLD";
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if ((chars[i] == 'A' || chars[i] == 'E' || chars[i] == 'I' || chars[i] == 'O' || chars[i] == 'U') && (chars[i] + 2 <= 'Z')) {
                chars[i] += 2;
            }
        }
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= 'a' && chars[i] <= 'z') {
                chars[i] -= 32;
            } else if (chars[i] >= 'A' && chars[i] <= 'Z') {
                chars[i] += 32;
            }
        }
        String expected = new String(chars);
        assertEquals(expected, Encode.encode(input));
    }
    @Test
    public void testEncodeEmptyString() {
        String input = "";
        String expected = "";
        assertEquals(expected, Encode.encode(input));
    }
    @Test
    public void EncodeTest_EmptyString() {
    	String message = "";
    	String expectedResult = "";
    	assertEquals(expectedResult, Encode.encode(message));
    }
    @Test
    public void EncodeTest_NullInput() {
    	String message = null;
    	assertThrows(NullPointerException.class, () -> Encode.encode(message));
    }
                                    
}