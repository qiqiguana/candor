package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of LargestDivisor.
*/
class LargestDivisorTest {
    @Test
    void testLargestDivisor_NegativeNumber_Returns1() {
        // Arrange & Act
        int result = LargestDivisor.largestDivisor(-15);
        // Assert
        assertEquals(1, result);
    }
    
    @Test
        public void testNothing(){
            LargestDivisor s = new LargestDivisor();
            }
    @Test
    void testLargestDivisorWithEvenNumber() {
        int result = LargestDivisor.largestDivisor(10);
        assertEquals(5, result);
    }
                                    
}