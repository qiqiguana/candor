package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SameChars.
*/
class SameCharsTest {
    @Test
    void testSameChars_withDifferentCharacters_ReturnsFalse() {
        String s0 = "abcd";
        String s1 = "abcdddddabce";
        Boolean result = SameChars.sameChars(s0, s1);
        assertFalse(result);
    }
}