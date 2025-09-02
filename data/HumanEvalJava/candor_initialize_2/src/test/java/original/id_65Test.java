package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CircularShift.
*/
class CircularShiftTest {
    @Test
    void circularShift_reverses_digits_when_shift_is_greater_than_length() {
        int x = 1234;
        int shift = 5;
        String expected = "4321";
        String result = CircularShift.circularShift(x, shift);
        assertEquals(expected, result);
    }
    
    @Test
        public void testNothing(){
            CircularShift s = new CircularShift();
            }
    @Test
    public void TestCircularShift_RightShiftWithinBounds() {
        int x = 100;
        int shift = 2;
        String expected = "001";
        String result = CircularShift.circularShift(x, shift);
        assertEquals(expected, result);
    }
                                    
}