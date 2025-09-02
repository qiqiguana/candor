package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of PrimeFib.
*/
class PrimeFibTest {
    @Test
    void primeFib_FirstCall_Returns2() {
        assertEquals(2, PrimeFib.primeFib(1));
    }
    
    @Test
        public void testNothing(){
            PrimeFib s = new PrimeFib();
            }
    @Test
    public void testPrimeFib_Input_1_Fixed() {
        int n = 2;
        int expectedResult = 3;
        int actualResult = PrimeFib.primeFib(n);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testPrimeFib_Input_10() {
        int n = 10;
        long expectedResult = 433494437;
        long actualResult = PrimeFib.primeFib(n);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void PrimeFibTest_isPrimeFalseForNumbersLessThanOrEqualTo1() {
    	int result = PrimeFib.primeFib(100);
    	assertNotEquals(2, result);
    }
                                    
}