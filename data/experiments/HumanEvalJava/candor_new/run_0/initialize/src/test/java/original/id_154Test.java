package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CycpatternCheck.
*/
class CycpatternCheckTest {
    @Test
    void testCycPatternTrue() {
        assertTrue(CycpatternCheck.cycpatternCheck("hello","ell"));
    }
}