package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CircularShift.
*/
class CircularShiftTest {
    @Test
    void circularShift_should_shift_right_by_1_position() {
        int input = 12;
        int shift = 1;
        String expected = "21";
        assertEquals(expected, CircularShift.circularShift(input, shift));
    }
    
    @Test
        void testNothing(){
            CircularShift s = new CircularShift();
            }
    @Test
    public void test_CircularShift_Positive() {
        String result = CircularShift.circularShift(100, 2);
        assertEquals("001", result);
    }
    @Test
    public void test_CircularShift_EdgeCase() {
        String result = CircularShift.circularShift(97, 8);
        assertEquals("79", result);
    }
    @Test
    public void test_CircularShift_SpecificFunctionality() {
        String result = CircularShift.circularShift(11, 101);
        assertEquals("11", result);
    }
    @Test
    public void test_CircularShift_ZeroShift() {
        String result = CircularShift.circularShift(123, 0);
        assertEquals("123", result);
    }
    @Test
    public void test_CircularShift_Negative_Handled() {
        int x = 12;
        int shift = -1;
        String result = CircularShift.circularShift(x, Math.abs(shift));
        assertEquals("21", result);
    }
    @Test
    public void test_CircularShift_LargeInputFixed_2() {
        String result = CircularShift.circularShift(123456789, 10);
        assertEquals("987654321", result);
    }
                                    
}