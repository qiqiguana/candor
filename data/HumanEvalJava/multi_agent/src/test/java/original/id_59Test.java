package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of LargestPrimeFactor.
*/
class LargestPrimeFactorTest {

    @Test
    void testLargestPrimeFactor_SimpleCase() {
        int input = 2048;
        int expectedOutput = 2;
        assertEquals(expectedOutput, LargestPrimeFactor.largestPrimeFactor(input));
    }
}