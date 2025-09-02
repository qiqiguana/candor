package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SumToN.
*/
class SumToNTest {
    @Test
    void testSumToN_HappyPath() {
        int n = 5;
        int expected = 15;
        int actual = SumToN.sumToN(n);
        assertEquals(expected, actual);
    }
    
    @Test
        void testNothing(){
            SumToN s = new SumToN();
            }
    @Test
    public void testSumToNSmallInput() {
        int n = 5;
        int expectedResult = 15;
        int actualResult = SumToN.sumToN(n);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testSumToNMediumInput() {
        int n = 10;
        int expectedResult = 55;
        int actualResult = SumToN.sumToN(n);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testSumToNLargeInput() {
        int n = 100;
        int expectedResult = 5050;
        int actualResult = SumToN.sumToN(n);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testSumToNZeroInput() {
        int n = 0;
        assertThrows(IllegalArgumentException.class, () -> SumToN.sumToN(n));
    }
    @Test
    public void testSumToNOneInput() {
        int n = 1;
        int expectedResult = 1;
        int actualResult = SumToN.sumToN(n);
        assertEquals(expectedResult, actualResult);
    }
                                    
}