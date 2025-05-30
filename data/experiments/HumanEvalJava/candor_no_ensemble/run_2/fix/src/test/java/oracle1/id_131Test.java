package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Digits.
*/
class DigitsTest {
    @Test
    void testDigits_ReturnsProductOfOddDigits() {
        int result = Digits.digits(235);
        assertEquals(15, result);
    }
    
    @Test
        void testNothing(){
            Digits s = new Digits();
            }
    @Test
    public void testDigits_SingleDigitNumber() {
        int result = Digits.digits(1);
        assertEquals(1, result);
    }
    @Test
    public void testDigits_EvenDigitNumber() {
        int result = Digits.digits(4);
        assertEquals(0, result);
    }
    @Test
    public void testDigits_Zero() {
        int result = Digits.digits(0);
        assertEquals(0, result);
    }
    @Test
    public void testDigits_MultipleOddDigits() {
        int result = Digits.digits(235);
        assertEquals(15, result);
    }
    @Test
    public void testDigits_MultipleEvenDigits() {
        int result = Digits.digits(2468);
        assertEquals(0, result);
    }
    @Test
    public void testDigits_LargeNumber() {
        int result = Digits.digits(5576543);
        assertEquals(2625, result);
    }
                                    
}