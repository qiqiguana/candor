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
}