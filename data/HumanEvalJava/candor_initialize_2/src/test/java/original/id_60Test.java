package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SumToN.
*/
class SumToNTest {

    @Test
    void testSumToN_SimpleCase() {
        int n = 5;
        int expectedResult = 15;
        int actualResult = SumToN.sumToN(n);
        assertEquals(expectedResult, actualResult);
    }
    
    @Test
        public void testNothing(){
            SumToN s = new SumToN();
            }
                                    
}