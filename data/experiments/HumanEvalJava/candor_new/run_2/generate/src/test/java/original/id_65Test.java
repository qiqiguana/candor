package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CircularShift.
*/
class CircularShiftTest {
    @Test
    void test_circularShift_shiftGreaterThanLength_reversesDigits() {
        String result = CircularShift.circularShift(12, 3);
        assertEquals("21", result);
    }
}