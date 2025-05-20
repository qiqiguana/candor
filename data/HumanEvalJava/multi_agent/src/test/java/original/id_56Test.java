package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CorrectBracketing.
*/
class CorrectBracketingTest {
    @Test
    void testCorrectBracketing_MultipleConsecutivePairs_ReturnsTrue() {
        String input = "<><><>";
        Boolean result = CorrectBracketing.correctBracketing(input);
        assertTrue(result);
    }
}