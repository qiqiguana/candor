package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of PrimeFib.
*/
class PrimeFibTest {
    @Test
    void testPrimeFib_FirstPrimeFibonacciNumber_Returns2() {
        // Arrange and Act
        int result = PrimeFib.primeFib(1);
        // Assert
        assertEquals(2, result);
    }
    @Test
    public void testPrimeFib_SmallN() {
        int input = 1;
        int expectedResult = 2;
        int actualResult = PrimeFib.primeFib(input);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testPrimeFib_MediumN() {
        int input = 5;
        int expectedResult = 89;
        int actualResult = PrimeFib.primeFib(input);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testPrimeFib_LargeN() {
        int input = 10;
        int expectedResult = 433494437;
        int actualResult = PrimeFib.primeFib(input);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testPrimeFibHappyPath() {
    	int[] inputs = {1, 2, 3, 4, 5};
    	int[] expectedResults = {2, 3, 5, 13, 89};
    	for (int i = 0; i < inputs.length; i++) {
    		assertEquals(expectedResults[i], PrimeFib.primeFib(inputs[i]));
    	}
    }
    @Test
    public void testPrimeFibEdgeCaseLargeInput() {
    	int input = 10;
    	assertEquals(433494437, PrimeFib.primeFib(input));
    }
    @Test
    public void testPrimeFibPrimeNumbersCorrected() {
        int[] inputs = {1, 2, 3, 4, 5};
        int[] expectedResults = {2, 3, 5, 13, 89};
        for (int i = 0; i < inputs.length; i++) {
            assertEquals(expectedResults[i], PrimeFib.primeFib(inputs[i]));
        }
    }
    @Test
    public void testPrimeFibPositiveInput() {
    	int[] input = {1, 2, 3, 4, 5};
    	int[] expectedOutput = {2, 3, 5, 13, 89};
    	for (int i = 0; i < input.length; i++) {
    		assertEquals(expectedOutput[i], PrimeFib.primeFib(input[i]));
    	}
    }
    @Test
    public void testPrimeFibLargeInput() {
    	int[] input = {6, 7, 8, 9, 10};
    	long[] expectedOutput = {233, 1597, 28657, 514229, 433494437};
    	for (int i = 0; i < input.length; i++) {
    		assertEquals(expectedOutput[i], (long)PrimeFib.primeFib(input[i]));
    	}
    }
}