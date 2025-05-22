package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of LargestDivisor.
*/
class LargestDivisorTest {
    @Test
    void testLargestDivisor_Returns5_WhenInputIs15() {
        int result = LargestDivisor.largestDivisor(15);
        assertEquals(5, result);
    }
}