package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FlipCase.
*/
class FlipCaseTest {
    @Test
    void testFlipCase_WhenInputStringHasNoUpperCaseLetters_ReturnsTheSameStringButAllInLowerCase() {
        String input = "hello";
        String expectedOutput = "HELLO";
        assertEquals(expectedOutput, FlipCase.flipCase(input));
    }
}