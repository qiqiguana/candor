package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of LargestPrimeFactor.
*/
class LargestPrimeFactorTest {
    @Test
    void largestPrimeFactorShouldReturn2WhenInputIs2048() {
        int input = 2048;
        int expectedOutput = 2;
        assertEquals(expectedOutput, LargestPrimeFactor.largestPrimeFactor(input));
    }
}
