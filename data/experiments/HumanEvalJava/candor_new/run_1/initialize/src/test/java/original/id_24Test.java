package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of LargestDivisor.
*/
class LargestDivisorTest {
    @Test
    void testLargestDivisorShouldReturnOneForPrimeNumber() {
        assertEquals(1, LargestDivisor.largestDivisor(7));
    }
}