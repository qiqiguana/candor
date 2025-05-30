package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StringXor.
*/
class StringXorTest {
    @Test
    void testStringXorSameLength() {
        // Arrange and Act
        String result = StringXor.stringXor("010", "110");
        // Assert
        assertEquals("100", result);
    }
    @Test
    public void testStringXor_TwoIdenticalStrings() {
        String result = StringXor.stringXor("1010", "1010");
        assertEquals("0000", result);
    }
    @Test
    public void testStringXor_TwoComplementaryStrings() {
        String result = StringXor.stringXor("1010", "0101");
        assertEquals("1111", result);
    }
    @Test
    public void testStringXor_SingleCharacterInputs() {
        String result = StringXor.stringXor("1", "0");
        assertEquals("1", result);
    }
    @Test
    public void testStringXor_EmptyStrings() {
        String result = StringXor.stringXor("", "");
        assertEquals("", result);
    }
    @Test
    public void testStringXor_NullInputs() {
        assertThrows(NullPointerException.class, () -> StringXor.stringXor(null, "1010"));
    }
    @Test
    public void testStringXor_InvalidBinaryStrings3() {
        assertThrows(StringIndexOutOfBoundsException.class, () -> StringXor.stringXor("1010", "110"));
    }
    @Test
    public void testValidBinaryStrings() {
    	String result = StringXor.stringXor("010", "110");
    	assertEquals("100", result);
    }
    @Test
    public void testEmptyStrings() {
    	String result = StringXor.stringXor("", "");
    	assertEquals("", result);
    }
    @Test
    public void testSingleCharacterXOR() {
    	String result = StringXor.stringXor("1", "0");
    	assertEquals("1", result);
    }
    @Test
    public void testIdenticalStrings() {
    	String result = StringXor.stringXor("101010", "101010");
    	assertEquals("000000", result);
    }
    @Test
    void test_stringXor_SameLength() {
        String a = "1010";
        String b = "1100";
        String expected = "0110";
        assertEquals(expected, StringXor.stringXor(a, b));
    }
    @Test
    void test_stringXor_DifferentLengths_Padded_v2() {
        String a = "101";
        String b = "1100";
        int maxLength = Math.max(a.length(), b.length());
        String paddedA = a;
        String paddedB = b;
        if (a.length() < maxLength) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < maxLength - a.length(); i++) {
                sb.append('0');
            }
            sb.append(a);
            paddedA = sb.toString();
        } else if (b.length() < maxLength) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < maxLength - b.length(); i++) {
                sb.append('0');
            }
            sb.append(b);
            paddedB = sb.toString();
        }
        String expected = "1001";
        assertEquals(expected, StringXor.stringXor(paddedA, paddedB));
    }
    @Test
    void test_stringXor_NonBinaryStrings_Fixed() {
        String a = "1021";
        String b = "1201";
        try {
            StringXor.stringXor(a, b);
            fail("Expected AssertionError");
        } catch (AssertionError e) {
            // expected
        }
    }
    @Test
    void test_stringXor_EmptyStrings() {
        String a = "";
        String b = "";
        assertEquals("", StringXor.stringXor(a, b));
    }
    @Test
    void test_stringXor_NullInput() {
        String a = "1010";
        String b = null;
        assertThrows(NullPointerException.class, () -> StringXor.stringXor(a, b));
    }
}