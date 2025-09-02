package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CycpatternCheck.
*/
class CycpatternCheckTest {
    @Test
    void testCycPatternCheck_PatternNotFound_ReturnsFalse() {
        String a = "abc";
        String b = "def";
        Boolean result = CycpatternCheck.cycpatternCheck(a, b);
        assertFalse(result);
    }
}