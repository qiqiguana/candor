package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ClosestInteger.
*/
class ClosestIntegerTest {
    @Test
    void testClosestInteger_RoundAwayFromZero_PositiveNumber() {
        String value = "14.5";
        int expectedResult = 15;
        int actualResult = ClosestInteger.closestInteger(value);
        assertEquals(expectedResult, actualResult);
    }
}