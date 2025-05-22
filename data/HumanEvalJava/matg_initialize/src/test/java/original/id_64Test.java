package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of VowelsCount.
*/
class VowelsCountTest {
    @Test
    void testVowelsCount_withStringContainingOnlyVowels_ReturnsCorrectVowelCount() {
        String s = "aeiouAEIOU";
        int expected = 10;
        int actual = VowelsCount.vowelsCount(s);
        assertEquals(expected, actual);
    }
}