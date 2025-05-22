package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsBored.
*/
class IsBoredTest {
    @Test
    void testIsBoredWithMultipleSentences() {
        String input = "I love It ! The sky is blue. The sun is shining.";
        int expected = 1;
        assertEquals(expected, IsBored.isBored(input));
    }
}