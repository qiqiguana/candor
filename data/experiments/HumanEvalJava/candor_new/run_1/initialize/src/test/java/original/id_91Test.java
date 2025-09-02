package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsBored.
*/
class IsBoredTest {
    @Test
    void testIsBoredWithMultipleSentences() {
        String input = "I love this weather. The sun is shining. I love that";
        int expected = 2;
        assertEquals(expected, IsBored.isBored(input));
    }
}