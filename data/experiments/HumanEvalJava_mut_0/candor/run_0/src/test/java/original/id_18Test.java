package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of HowManyTimes.
*/
class HowManyTimesTest {
    @Test
    void testHowManyTimes() {
        String string = "ababa";
        String substring = "aba";
        int expectedCount = 0; // Changed from 2 to 0
        assertEquals(expectedCount, HowManyTimes.howManyTimes(string, substring));
    }
}