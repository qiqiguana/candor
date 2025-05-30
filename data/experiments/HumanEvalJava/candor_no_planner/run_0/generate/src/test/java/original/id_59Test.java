package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of LargestPrimeFactor.
*/
class LargestPrimeFactorTest {
    @Test
    void testLargestPrimeFactor_2048_Returns2() {
        int expected = 2;
        int actual = LargestPrimeFactor.largestPrimeFactor(2048);
        assertEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            LargestPrimeFactor s = new LargestPrimeFactor();
            }
    @Test
    void testLargestPrimeFactorForSmallNumbers() {
    	int[][] inputs = {{2, 3, 5}, {7, 11, 13}};
    	int[] expectedResults = {2, 3, 5, 7, 11, 13};
    	for (int i = 0; i < inputs.length; i++) {
    		for (int j = 0; j < inputs[i].length; j++) {
    			assertEquals(expectedResults[i * inputs[i].length + j], LargestPrimeFactor.largestPrimeFactor(inputs[i][j]));
    		}
    	}
    }
                                    
}