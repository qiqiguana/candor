package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CorrectBracketing1.
*/
class CorrectBracketing1Test {
    @Test
    void testCorrectBracketing_OpenAndCloseBrackets_ReturnsTrue() {
        String brackets = "(()())";
        assertTrue(CorrectBracketing1.correctBracketing(brackets));
    }
}