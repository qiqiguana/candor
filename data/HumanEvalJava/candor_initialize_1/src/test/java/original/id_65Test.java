package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CircularShift.
*/
class CircularShiftTest {
    @Test
    void testCircularShift_RightShift() {
        // Arrange and Act
        String result = CircularShift.circularShift(12, 1);
        // Assert
        assertEquals("21", result);
    }
    
    @Test
        public void testNothing(){
            CircularShift s = new CircularShift();
            }
    @Test
    public void TestCircularShiftWithShiftGreaterThanNumberOfDigits() {
        int[] input = {97, 8};
        String expectedResult = "79";
        assertEquals(expectedResult, CircularShift.circularShift(input[0], input[1]));
    }
                                    
}