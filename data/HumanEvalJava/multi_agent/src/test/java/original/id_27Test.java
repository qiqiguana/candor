package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FlipCase.
*/
class FlipCaseTest {
    @Test
    void testFlipCase() {
        String result = FlipCase.flipCase("Hello");
        assertEquals("hELLO", result);
    }
}
