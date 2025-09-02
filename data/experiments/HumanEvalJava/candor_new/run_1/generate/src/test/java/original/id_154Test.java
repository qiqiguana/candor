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
    
    @Test
        public void testNothing(){
            CycpatternCheck s = new CycpatternCheck();
            }
    @Test
    public void test_cycpatternCheck_false() {
        String a = "xyzw";
        String b = "xyw";
        assertFalse(CycpatternCheck.cycpatternCheck(a, b));
    }
                                    
}