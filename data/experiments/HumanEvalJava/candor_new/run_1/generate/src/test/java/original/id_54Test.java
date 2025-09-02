package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SameChars.
*/
class SameCharsTest {
    @Test
    void testSameChars_DifferentWords_ReturnsFalse() {
        String s0 = "abcd";
        String s1 = "dddddddabce";
        Boolean result = SameChars.sameChars(s0, s1);
        assertFalse(result);
    }
    
    @Test
        public void testNothing(){
            SameChars s = new SameChars();
            }
    @Test
    public void testSameCharsWithDifferentLengths() {
        String s0 = "eabcdzzzz";
        String s1 = "dddzzzzzzzddeddabc";
        boolean expected = true;
        boolean result = SameChars.sameChars(s0, s1);
        assertEquals(expected, result);
    }
                                    
}