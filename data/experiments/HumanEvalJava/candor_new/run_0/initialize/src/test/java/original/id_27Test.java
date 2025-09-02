package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FlipCase.
*/
class FlipCaseTest {
    @Test
    void flipCase_shouldFlipLowercaseCharactersToUppercaseAndUppercaseToLowercase() {
        String result = FlipCase.flipCase("Hello!");
        assertEquals("hELLO!", result);
    }
}