package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Encode.
*/
class EncodeTest {
    @Test
    void testEncode_SingleWordLowerCase_VowelsShifted() {
        String message = "test";
        String expected = "TGST";
        String actual = Encode.encode(message);
        assertEquals(expected, actual);
    }
    
    @Test
     void testNothing(){
         Encode s = new Encode();
         }
    @Test
    void testMultipleWords() {
        String message = "This is a message";
        assertEquals("tHKS  KS  C  MGSSCGG", Encode.encode(message));
    }
    @Test
    void testEmptyString() {
        String message = "";
        assertEquals("", Encode.encode(message));
    }
    @Test
    void testSingleCharacter() {
        String message = "a";
        assertEquals("C", Encode.encode(message));
    }
                                  
}