package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsEqualToSumEven.
*/
class IsEqualToSumEvenTest {
    @Test
    void isEqualToSumEven_WhenNumberIsLessThan8_ReturnsFalse() {
        assertFalse(IsEqualToSumEven.isEqualToSumEven(4));
    }
    
    @Test
        public void testNothing(){
            IsEqualToSumEven s = new IsEqualToSumEven();
            }
    @Test
    public void isEqualToSumEven_PositiveTest_1() {
        assertEquals(true, IsEqualToSumEven.isEqualToSumEven(10));
    }
    @Test
    public void isEqualToSumEven_NegativeTest_1() {
        assertEquals(false, IsEqualToSumEven.isEqualToSumEven(4));
    }
    @Test
    public void isEqualToSumEven_EdgeCaseTest_1() {
        assertEquals(true, IsEqualToSumEven.isEqualToSumEven(8));
    }
    @Test
    public void isEqualToSumEven_NegativeTest_2() {
        assertEquals(false, IsEqualToSumEven.isEqualToSumEven(11));
    }
    @Test
    public void isEqualToSumEven_PositiveTest_2() {
        assertEquals(true, IsEqualToSumEven.isEqualToSumEven(12));
    }
                                    
}