package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SameChars.
*/
class SameCharsTest {
    @Test
    void testSameChars_DifferentWords_ReturnsTrue() {
        String s0 = "abcd";
        String s1 = "dddddddabc";
        Boolean result = SameChars.sameChars(s0, s1);
        assertTrue(result);
    }
    
    @Test
        public void testNothing(){
            SameChars s = new SameChars();
            }
    @Test
    public void testSameCharsClassInitialization() {
        new original.SameChars();
    }
    @Test
    public void testSameCharsFalseCase() {
        assertFalse(original.SameChars.sameChars("abcd", "ddd"));
    }
                                    
}