package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of LargestPrimeFactor.
*/
class LargestPrimeFactorTest {
    @Test
    void test_largest_prime_factor_15_is_5() {
        int n = 15;
        int expected = 5;
        int result = LargestPrimeFactor.largestPrimeFactor(n);
        assertEquals(expected, result);
    }
    
    @Test
        public void testNothing(){
            LargestPrimeFactor s = new LargestPrimeFactor();
            }
                                    
}