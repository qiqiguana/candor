package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CorrectBracketing1.
*/
class CorrectBracketing1Test {
    @Test
    void testCorrectBracketing_EmptyString_ReturnsTrue() {
        String input = "";
        Boolean result = CorrectBracketing1.correctBracketing(input);
        assertTrue(result);
    }
    
    @Test
        public void testNothing(){
            CorrectBracketing1 s = new CorrectBracketing1();
            }
    @Test
    public void testOnlyOpenBrackets() {
        String input = "(((()";
        assertFalse(CorrectBracketing1.correctBracketing(input));
    }
    @Test
    public void testOnlyCloseBrackets() {
        String input = "))))";
        assertFalse(CorrectBracketing1.correctBracketing(input));
    }
    @Test
    public void testCorrectBracketingMultipleSets() {
        String input = "(()())(()())";
        assertTrue(CorrectBracketing1.correctBracketing(input));
    }
    @Test
    public void testCorrectBracketingNested() {
        String input = "((()))";
        assertTrue(CorrectBracketing1.correctBracketing(input));
    }
                                    
}