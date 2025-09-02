package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CircularShift.
*/
class CircularShiftTest {
    @Test
    void testCircularShift_singleDigitShiftLessThanLength() {
        String result = CircularShift.circularShift(12, 1);
        assertEquals("21", result);
    }
    
    @Test
        public void testNothing(){
            CircularShift s = new CircularShift();
            }
    @Test
    public void testCircularShift_WithSingleDigitInputAndLargeShift() {
        String result = CircularShift.circularShift(5, 10);
        assertEquals("5", result);
    }
                                    
}