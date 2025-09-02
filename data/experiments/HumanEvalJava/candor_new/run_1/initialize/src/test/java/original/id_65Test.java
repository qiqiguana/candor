package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CircularShift.
*/
class CircularShiftTest {
    @Test
    void test_circularShift_shift_greater_than_length() {
        String result = CircularShift.circularShift(123, 4);
        assertEquals("321", result);
    }
}