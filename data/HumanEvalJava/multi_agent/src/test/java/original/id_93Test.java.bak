package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Encode.
*/
class EncodeTest {
    @Test
    public void testEncode() {
        // Test code here
    }
    @Test
    void testEncodeClassInitialization() {
        // Test case: No exception should be thrown
        assertDoesNotThrow(() -> new original.Encode());
    }
    @Test
    void testEmptyMessageEncoding() {
        String input = "";
        String expected = "";
        assertEquals(expected, original.Encode.encode(input));
    }
    @Test
    void testLowercaseVowelEncoding1() {
        String input = "a";
        String expected = "C";
        assertEquals(expected, original.Encode.encode(input));
    }
    @Test
    void testLowercaseVowelEncoding4() {
        String input = "u";
        String expected = "W";
        assertEquals(expected, original.Encode.encode(input));
    }
    public class TestEncode {
    @Test
    void testEncode() {
    String expected = "TGST";
    String actual = Encode.encode("test");
    assertEquals(expected, actual);
    }
    @Test
    public void testEmptyStringEncoding() {
        assertEquals("", Encode.encode(""));}
    @Test
    public void testSingleUppercaseLetterEncoding() {
        assertEquals("C", Encode.encode("A"));}
    @Test
    public void testSingleLowercaseLetterEncoding() {
        assertEquals("B", Encode.encode("z"));}
    @Test
    public void testMixedCaseLetterEncoding() {
        assertEquals("Ifmmp, Xpsme!", Encode.encode("Hello, World!"));}
    @Test
    public void testSingleUppercaseVowelEncoding() {
        assertEquals("E", Encode.encode("A"));}
    @Test
    public void testSingleLowercaseVowelEncoding() {
        assertEquals("o", Encode.encode("a"));}
    public void testEncodeMessage() { String result = Encode.encode("Hello, World!"); assertEquals("Ifmmp, Xpsme!", result); }
    public void testEncodeUppercaseVowel1() { String result = Encode.encode("E"); assertEquals("G", result); }
    public void testEncodeUppercaseVowel() { String result = Encode.encode("A"); assertEquals("C", result); }
    public void testEncodeLowercaseVowel() { String result = Encode.encode("e"); assertEquals("G", result); }
    }
}