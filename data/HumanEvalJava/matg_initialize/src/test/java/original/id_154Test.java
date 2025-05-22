package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CycpatternCheck.
*/
class CycpatternCheckTest {
    @Test
    void testCycpatternCheck_RotatePatternFound_ReturnsTrue() {
        String a = "yello";
        String b = "ell";
        Boolean expected = true;
        Boolean actual = CycpatternCheck.cycpatternCheck(a, b);
        assertEquals(expected, actual);
    }
}