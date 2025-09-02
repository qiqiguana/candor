package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CycpatternCheck.
*/
class CycpatternCheckTest {
    @Test
    void testCycPatternSuccess() {
        String a = "hello";
        String b = "ell";
        assertTrue(CycpatternCheck.cycpatternCheck(a, b));
    }
}
