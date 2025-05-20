package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ClosestInteger.
*/
class ClosestIntegerTest {
    @Test
    void testClosestInteger_RoundAwayFromZero() {
        String value = "14.5";
        int expected = 15;
        assertEquals(expected, ClosestInteger.closestInteger(value));
    }
}