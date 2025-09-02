package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsEqualToSumEven.
*/
class IsEqualToSumEvenTest {
    @Test
    void testIsEqualSumEven_WhenInput8_ReturnsTrue() {
        Boolean result = IsEqualToSumEven.isEqualToSumEven(8);
        assertTrue(result);
    }
    
    @Test
        public void testNothing(){
            IsEqualToSumEven s = new IsEqualToSumEven();
            }
    @Test
    public void isEqualToSumEven_false_for_n_less_than_8() {
        assertFalse(IsEqualToSumEven.isEqualToSumEven(7));
    }
    @Test
    public void isEqualToSumEven_false_for_odd_n_greater_than_or_equal_to_8() {
        assertFalse(IsEqualToSumEven.isEqualToSumEven(9));
    }
                                    
}