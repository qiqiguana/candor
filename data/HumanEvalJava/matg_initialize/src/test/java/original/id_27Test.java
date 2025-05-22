package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FlipCase.
*/
class FlipCaseTest {
    @Test
    void testFlipCaseWithMixedCaseString() {
        String input = "Hello";
        String expectedOutput = "hELLO";
        assertEquals(expectedOutput, FlipCase.flipCase(input));
    }
}