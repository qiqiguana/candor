package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RoundedAvg.
*/
class RoundedAvgTest {
    @Test
    void testRoundedAverage_ReturnsMinusOne_WhenNIsGreaterThanM() {
        Object result = RoundedAvg.roundedAvg(7, 5);
        assertEquals(-1, result);
    }
}