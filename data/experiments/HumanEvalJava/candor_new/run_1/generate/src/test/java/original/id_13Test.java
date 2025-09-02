package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GreatestCommonDivisor.
*/
class GreatestCommonDivisorTest {
    @Test
    void testGreatestCommonDivisor_DifferentNumbers_ReturnsCorrectResult() {
        // Arrange & Act
        int result = GreatestCommonDivisor.greatestCommonDivisor(25, 15);
        // Assert
        assertEquals(5, result);
    }
    
    @Test
        public void testNothing(){
            GreatestCommonDivisor s = new GreatestCommonDivisor();
            }
                                    
}