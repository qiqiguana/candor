package original;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Encode.
*/
class EncodeTest {
    @Test
    void testEncode() {
        String message = "test";
        String expected = "TGST";
        String actual = Encode.encode(message);
        assertEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            Encode s = new Encode();
            }
    @Test
    public void TestEncodeNegative() {
    	String expected = "";
    	String actual = Encode.encode("");
    	assertEquals(expected, actual);
    }
    @Test
    public void TestEncodeMixedCaseVowels1() {
    	String expected = "hGLLQ wQRLD";
    	String actual = Encode.encode("Hello World");
    	assertEquals(expected, actual);
    }
    @Test
    public void TestEncodeEdgeCaseVowelAtEndFixed() {
        String expected = "CGKQW";
        String actual = Encode.encode("aeiou");
        assertEquals(expected, actual);
    }
    @Test
    public void testEncodeAllLowercase() {
        String expected = "HGLLQ WQRLD";
        String actual = Encode.encode("hello world");
        assertEquals(expected, actual);
    }
    @Test
    public void testEmptyString() {
    String input = "";
    String expected = "";
    assertEquals(expected, Encode.encode(input));
    }
    @Test
    public void testEncode_EmptyString() {
        String message = "";
        String expected = "";
        assertEquals(expected, Encode.encode(message));
    }
    @Test
    public void testEncodeWithLowercaseLetters() {
    	String message = "test";
    	String expected = "TGST";
    	assertEquals(expected, Encode.encode(message));
    }
    @Test
    public void testEncodeWithMixedCaseLetters() {
    	String message = "TeSt";
    	String expected = "tGsT";
    	assertEquals(expected, Encode.encode(message));
    }
    @Test
    public void testEncodeWithSingleCharacter() {
    	String message = "a";
    	String expected = "C";
    	assertEquals(expected, Encode.encode(message));
    }
    @Test
    public void testEncodeWithEmptyString() {
    	String message = "";
    	String expected = "";
    	assertEquals(expected, Encode.encode(message));
    }
    @Test
    public void testEncodeWithNullInput() {
    	String message = null;
    	assertThrows(NullPointerException.class, () -> Encode.encode(message));
    }
    @Test
    public void testEncodeWithSingleCharacterFixed() {
        String result = Encode.encode("a");
        assertEquals('C', result.charAt(0));
    }
    @Test
    public void testEncodeWithEmptyString1() {
        String result = Encode.encode("");
        assertEquals("", result);
    }
                                    
}