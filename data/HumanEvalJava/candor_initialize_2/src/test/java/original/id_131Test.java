package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Digits.
*/
class DigitsTest {

    @Test
    void testDigits_OddNumber_ReturnsProductOfOddDigits() {
        // Arrange and Act
        int result = Digits.digits(235);
        // Assert
        assertEquals(15, result);
    }
    
    @Test
        public void testNothing(){
            Digits s = new Digits();
            }
    @Test
    public void TestClassInitialization1() {
    	assertDoesNotThrow(() -> new Digits());
    }
    @Test
    public void TestClassInitialization2() {
    	Digits digits = new Digits();
    	assertNotNull(digits);
    }
    @Test
    public void testProductOfOddDigitsWithAllOdd() {
        int n = 13579;
        int result = original.Digits.digits(n);
        assertEquals(945, result);
    }
    @Test
    public void testProductOfOddDigitsWithNoOdd() {
        int n = 2468;
        int result = original.Digits.digits(n);
        assertEquals(0, result);
    }
                                    
}