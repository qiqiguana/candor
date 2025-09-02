package original;

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
}
