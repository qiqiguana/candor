package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CorrectBracketing1.
*/
class CorrectBracketing1Test {
    @Test
    void test_correctBracketing_balancedBrackets() {
        String brackets = "(()())";
        assertTrue(CorrectBracketing1.correctBracketing(brackets));
    }
    
    @Test
        public void testNothing(){
            CorrectBracketing1 s = new CorrectBracketing1();
            }
    @Test
    public void test_correctBracketing_mismatched_closing_bracket() {
        String input = "((()())))";
        Boolean expected_result = false;
        assertEquals(expected_result, CorrectBracketing1.correctBracketing(input));
    }
    @Test
    public void test_correctBracketing_unbalanced_brackets_at_beginning_2() {
        String input = "(())(()";
        Boolean expected_result = false;
        assertEquals(expected_result, CorrectBracketing1.correctBracketing(input));
    }
                                    
}