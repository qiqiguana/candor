package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of LargestPrimeFactor.
*/
class LargestPrimeFactorTest {
    @Test
    void testLargestPrimeFactor() {
        int expected = 29;
        int actual = LargestPrimeFactor.largestPrimeFactor(13195);
        assertEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            LargestPrimeFactor s = new LargestPrimeFactor();
            }
                                    
}