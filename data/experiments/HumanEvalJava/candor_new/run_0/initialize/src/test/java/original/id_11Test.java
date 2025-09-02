package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StringXor.
*/
class StringXorTest {
    @Test
    void testStringXor_SameLengthStrings_XORResult() {
        // Given two strings a and b consisting only of 1s and 0s
        String a = "010";
        String b = "110";
        // When performing binary XOR on these inputs
        String result = StringXor.stringXor(a, b);
        // Then the result should be the binary XOR of a and b
        assertEquals("100", result);
    }
}