package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CorrectBracketing.
*/
class CorrectBracketingTest {
    @Test
    void testCorrectBracketing1() {
        String brackets = "<><><<<><><>><<>";
        assertFalse(CorrectBracketing.correctBracketing(brackets));
    }
}