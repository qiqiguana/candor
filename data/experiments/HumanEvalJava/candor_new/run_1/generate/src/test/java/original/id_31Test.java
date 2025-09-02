package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsPrime.
*/
class IsPrimeTest {
    @Test
    void testIsPrimeForCompositeNumbers() {
        // Arrange and Act
        boolean result = IsPrime.isPrime(6);
        
        // Assert
        assertFalse(result);
    }
    
    @Test
        public void testNothing(){
            IsPrime s = new IsPrime();
            }
    @Test
    public void testIsPrime_ForNumberLessThanOrEqualTo1() {
        int[] inputs = {0, 1};
        boolean[] expectedResults = {false, false};
        for (int i = 0; i < inputs.length; i++) {
            boolean result = IsPrime.isPrime(inputs[i]);
            assertEquals(expectedResults[i], result);
        }
    }
    @Test
    public void testIsPrime_ForPrimeNumber() {
        int[] inputs = {101, 11, 5};
        boolean[] expectedResults = {true, true, true};
        for (int i = 0; i < inputs.length; i++) {
            boolean result = IsPrime.isPrime(inputs[i]);
            assertEquals(expectedResults[i], result);
        }
    }
                                    
}