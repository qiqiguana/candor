package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsBored.
*/
class IsBoredTest {
    @Test
    void testIsBored_ReturnsZero_WhenInputStringDoesNotContainISentences() {
        String input = "Hello world";
        int expected = 0;
        assertEquals(expected, IsBored.isBored(input));
    }
}