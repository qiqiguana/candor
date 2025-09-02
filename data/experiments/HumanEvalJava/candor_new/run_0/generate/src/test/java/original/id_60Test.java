package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SumToN.
*/
class SumToNTest {
    @Test
    void testSumToN_PositiveNumber_ReturnsCorrectSum() {
        // Arrange and Act
        int n = 5;
        int expectedResult = 15;
        int actualResult = SumToN.sumToN(n);
        
        // Assert
        assertEquals(expectedResult, actualResult);
    }
    
    @Test
        public void testNothing(){
            SumToN s = new SumToN();
            }
                                    
}