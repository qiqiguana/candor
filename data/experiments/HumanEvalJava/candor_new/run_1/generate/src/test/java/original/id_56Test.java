package original;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CorrectBracketing.
*/
class CorrectBracketingTest {
    @Test
    void testCorrectBracketing_BalancedBrackets_ReturnTrue() {
        String brackets = "<><><<><>><>";
        Boolean result = CorrectBracketing.correctBracketing(brackets);
        assertTrue(result);
    }
    
    @Test
        public void testNothing(){
            CorrectBracketing s = new CorrectBracketing();
            }
    @Test
    public void testCorrectBracketingMismatchedClosingBracket() {
        // Given
        String brackets = "><";
        // When
        Boolean result = CorrectBracketing.correctBracketing(brackets);
        // Then
        assertFalse(result);
    }
    @Test
    public void test_unbalanced_brackets_with_one_opening_bracket() {
        assertFalse(CorrectBracketing.correctBracketing("<"));
    }
                                    
}