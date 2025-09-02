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
    
    @Test
        public void testNothing(){
            LargestDivisor s = new LargestDivisor();
            }
    @Test
    public void test_largestDivisor_with_n_equals_1() {
        int result = LargestDivisor.largestDivisor(1);
        assertEquals(1, result);
    }
                                    
}