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
}