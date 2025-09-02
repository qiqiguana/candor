package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Digits.
*/
class DigitsTest {
    @Test
    void testDigits_HasOddDigit_ReturnsProductOfOddDigits() {
        int actual = Digits.digits(235);
        int expected = 15;
        assertEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            Digits s = new Digits();
            }
    @Test
    public void TestEvenDigitsProduct() {
        int[][] inputsAndExpectedResults = {{2468, 0}, {120, 1}};
        for (int[] inputAndExpectedResult : inputsAndExpectedResults) {
            int input = inputAndExpectedResult[0];
            int expectedResult = inputAndExpectedResult[1];
            assertEquals(expectedResult, Digits.digits(input));
        }
    }
                                    
}