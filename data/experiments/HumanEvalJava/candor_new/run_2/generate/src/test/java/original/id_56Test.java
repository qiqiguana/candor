package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CorrectBracketing.
*/
class CorrectBracketingTest {
    @Test
    void correctBracketing_MatchingBrackets_ReturnsTrue() {
        String brackets = "<<><>>";
        assertTrue(CorrectBracketing.correctBracketing(brackets));
    }
}