package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CircularShift.
*/
class CircularShiftTest {
    @Test
    void test_circular_shift_reverses_digits_when_shift_greater_than_length() {
        assertEquals("79", CircularShift.circularShift(97, 8));
    }
    
    @Test
        public void testNothing(){
            CircularShift s = new CircularShift();
            }
    @Test
    public void test_circular_shift_no_shift() {
        String result = CircularShift.circularShift(100, 0);
        assertEquals("100", result);
    }
    @Test
    public void test_circular_shift_invalid_input() {
        try {
            CircularShift.circularShift(Integer.parseInt("abc"), 2);
            fail("Expected NumberFormatException");
        } catch (NumberFormatException e) {
            assertEquals("Error: Input must be an integer.", "Error: Input must be an integer.");
        }
    }
    @Test
    public void test_circular_shift_greater_than_digits() {
        String result = CircularShift.circularShift(123, 4);
        assertEquals("321", result);
    }
    @Test
    public void test_circular_shift_zero_shift_single_digit() {
        String result = CircularShift.circularShift(5, 0);
        assertEquals("5", result);
    }
    @Test
    public void testHappyPath_SingleDigitShift() {
        String result = CircularShift.circularShift(7, 1);
        assertEquals("7", result);
    }
    @Test
    public void testEdgeCase_LargeShiftValue() {
        String result = CircularShift.circularShift(12, 5);
        assertEquals("21", result);
    }
    @Test
    public void testEdgeCase_ZeroShiftValue() {
        String result = CircularShift.circularShift(123, 0);
        assertEquals("123", result);
    }
    @Test
    public void testEdgeCase_SingleDigitWithLargeShift() {
        String result = CircularShift.circularShift(7, 10);
        assertEquals("7", result);
    }
    @Test
    public void testMultiDigitShiftRightByOne() {
        String result = CircularShift.circularShift(123, 1);
        assertEquals("312", result);
    }
                                    
}