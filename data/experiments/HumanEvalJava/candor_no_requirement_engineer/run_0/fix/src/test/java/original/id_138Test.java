package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsEqualToSumEven.
*/
class IsEqualToSumEvenTest {
    @Test
    void isEqualToSumEven_nLessThan8_ReturnsFalse() {
        // Arrange
        int n = 4;

        // Act
        boolean result = IsEqualToSumEven.isEqualToSumEven(n);

        // Assert
        assertFalse(result);
    }
    
    @Test
        void testNothing(){
            IsEqualToSumEven s = new IsEqualToSumEven();
            }
    @Test
    public void testIsEqualToSumEven_with_n_less_than_8() {
        int[] inputs = {4, 6, 7};
        boolean[] expectedResults = {false, false, false};
        for (int i = 0; i < inputs.length; i++) {
            boolean result = IsEqualToSumEven.isEqualToSumEven(inputs[i]);
            assertEquals(expectedResults[i], result);
        }
    }
    @Test
    public void testIsEqualToSumEven_with_n_equal_to_8() {
        int input = 8;
        boolean expectedResult = true;
        boolean result = IsEqualToSumEven.isEqualToSumEven(input);
        assertEquals(expectedResult, result);
    }
    @Test
    public void testIsEqualToSumEven_with_even_numbers_greater_than_8() {
        int[] inputs = {10, 12, 14};
        boolean[] expectedResults = {true, true, true};
        for (int i = 0; i < inputs.length; i++) {
            boolean result = IsEqualToSumEven.isEqualToSumEven(inputs[i]);
            assertEquals(expectedResults[i], result);
        }
    }
    @Test
    public void testIsEqualToSumEven_with_odd_numbers_greater_than_8() {
        int[] inputs = {11, 13, 15};
        boolean[] expectedResults = {false, false, false};
        for (int i = 0; i < inputs.length; i++) {
            boolean result = IsEqualToSumEven.isEqualToSumEven(inputs[i]);
            assertEquals(expectedResults[i], result);
        }
    }
    @Test
    public void testIsEqualToSumEven_with_large_even_numbers() {
        int[] inputs = {100, 200, 400};
        boolean[] expectedResults = {true, true, true};
        for (int i = 0; i < inputs.length; i++) {
            boolean result = IsEqualToSumEven.isEqualToSumEven(inputs[i]);
            assertEquals(expectedResults[i], result);
        }
    }
    @Test
    public void testIsEqualToSumEven_with_n_less_than_or_equal_to_0() {
        int[] inputs = {-1, 0};
        boolean[] expectedResults = {false, false};
        for (int i = 0; i < inputs.length; i++) {
            boolean result = IsEqualToSumEven.isEqualToSumEven(inputs[i]);
            assertEquals(expectedResults[i], result);
        }
    }
                                    
}