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
        boolean result = CorrectBracketing1.correctBracketing(input);
        assertTrue(result);
    }
    
    @Test
        public void testNothing(){
            CorrectBracketing1 s = new CorrectBracketing1();
            }
    @Test
    public void test_correct_bracketing_with_single_opening_bracket() {
    	String brackets = "(";
    	assertFalse(CorrectBracketing1.correctBracketing(brackets));
    }
    @Test
    public void test_correct_bracketing_with_single_closing_bracket() {
    	String brackets = ")";
    	assertFalse(CorrectBracketing1.correctBracketing(brackets));
    }
                                    
}