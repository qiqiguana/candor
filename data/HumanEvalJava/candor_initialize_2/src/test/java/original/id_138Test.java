package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsEqualToSumEven.
*/
class IsEqualToSumEvenTest {

    @Test
    void testIsEqualSumEven_NLessThan8_ReturnsFalse() {
        // Arrange and Act
        Boolean result = IsEqualToSumEven.isEqualToSumEven(4);

        // Assert
        assertFalse(result);
    }
    
    @Test
        public void testNothing(){
            IsEqualToSumEven s = new IsEqualToSumEven();
            }
    @Test
    public void isEqualToSumEven_evenNumberGreaterThanOrEqualTo8() {
        assertEquals(true, IsEqualToSumEven.isEqualToSumEven(10));
    }
    @Test
    public void isEqualToSumEven_oddNumberGreaterThanOrEqualTo8() {
        assertEquals(false, IsEqualToSumEven.isEqualToSumEven(11));
    }
                                    
}