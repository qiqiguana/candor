package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SameChars.
*/
class SameCharsTest {
    @Test
    void testSameChars_DifferentWords_ReturnsTrue() {
        String s0 = "eabcdzzzz";
        String s1 = "dddzzzzzzzddeddabc";
        Boolean result = SameChars.sameChars(s0, s1);
        assertTrue(result);
    }
}
