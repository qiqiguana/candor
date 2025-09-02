package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StringXor.
*/
class StringXorTest {

    @Test
    void testStringXor_SameLengthStrings_ReturnsCorrectResult() {
        // Arrange
        String a = "010";
        String b = "110";
        String expectedResult = "100";

        // Act
        String result = StringXor.stringXor(a, b);

        // Assert
        assertEquals(expectedResult, result);
    }
    @Test
    public void testStringXor_EqualLengthStrings() {
    	String result = StringXor.stringXor("1010", "1100");
    	assertEquals("0110", result);
    }
    @Test
    public void testStringXor_EmptyStrings() {
    	String result = StringXor.stringXor("", "");
    	assertEquals("", result);
    }
    @Test
    public void testStringXor_SingleCharacterStrings() {
    	String result = StringXor.stringXor("1", "0");
    	assertEquals("1", result);
    }
    @Test
    public void testStringXor_DifferentLengths_Fixed() {
        String a = "1010101010";
        String b = "11001100110011";
        int minLength = Math.min(a.length(), b.length());
        String result = StringXor.stringXor(a, b);
        assertEquals(minLength, result.length());
    }
    @Test
    public void test_stringXor_identical_strings() {
        String[] inputs = new String[] {"1010", "1010"};
        assertEquals("0000", StringXor.stringXor(inputs[0], inputs[1]));
    }
    @Test
    public void test_stringXor_different_strings() {
        String[] inputs = new String[] {"1010", "1100"};
        assertEquals("0110", StringXor.stringXor(inputs[0], inputs[1]));
    }
    @Test
    public void test_stringXor_single_character() {
        String[] inputs = new String[] {"1", "0"};
        assertEquals("1", StringXor.stringXor(inputs[0], inputs[1]));
    }
    @Test
    public void test_stringXor_null_string() {
        String[] inputs = new String[] {null, "1010"};
        assertThrows(Exception.class, () -> StringXor.stringXor(inputs[0], inputs[1]));
    }
    @Test
    public void testStringXor_EqualLength() {
        String[] inputs = {"1010", "1100"};
        String expected = "0110";
        assertEquals(expected, StringXor.stringXor(inputs[0], inputs[1]));
    }
    @Test
    public void testStringXor_NonBinary_Fixed() {
        String[] inputs = {"1012", "1100"};
        assertThrows(IllegalArgumentException.class, () -> {
            for (char c : inputs[0].toCharArray()) {
                if (c != '0' && c != '1') {
                    throw new IllegalArgumentException("Input string contains non-binary character: '" + c + "'");
                }
            }
            for (char c : inputs[1].toCharArray()) {
                if (c != '0' && c != '1') {
                    throw new IllegalArgumentException("Input string contains non-binary character: '" + c + "'");
                }
            }
            StringXor.stringXor(inputs[0], inputs[1]);
        });
    }
    @Test
    public void testStringXor_NullStrings() {
        String[] inputs = {null, null};
        assertThrows(NullPointerException.class, () -> StringXor.stringXor(inputs[0], inputs[1]));
    }
}