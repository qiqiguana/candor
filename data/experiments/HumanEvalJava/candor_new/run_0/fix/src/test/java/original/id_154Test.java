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
    
    @Test
        public void testNothing(){
            CycpatternCheck s = new CycpatternCheck();
            }
    @Test
    public void test_cycpatternCheck_with_no_match() {
        assertFalse(CycpatternCheck.cycpatternCheck("abcd", "abd"));
    }
                                    
}