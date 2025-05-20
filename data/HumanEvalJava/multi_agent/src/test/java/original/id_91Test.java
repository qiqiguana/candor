package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsBored.
*/
class IsBoredTest {
    @Test
    void testIsBored_SingleSentenceStartingWithI_Returns1() {
        String input = "I love this weather";
        int expected = 1;
        int actual = IsBored.isBored(input);
        assertEquals(expected, actual);
    }
}
