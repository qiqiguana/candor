package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CycpatternCheck.
*/
class CycpatternCheckTest {
    @Test
    void testCycPatternMatchFound() {
        String a = "hello";
        String b = "ell";
        assertTrue(CycpatternCheck.cycpatternCheck(a, b));
    }
    
    @Test
        public void testNothing(){
            CycpatternCheck s = new CycpatternCheck();
            }
    @Test
    public void testCycpatternCheckMethodReturnsFalseWhenPatternNotFound1() {
        String[] input = {"abcd", "abd"};
        assertFalse(CycpatternCheck.cycpatternCheck(input[0], input[1]));
    }
                                    
}