package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsSimplePower.
*/
class IsSimplePowerTest {
    @Test
    void testIsSimplePowerShouldReturnTrueForXEqualTo1() {
        // Arrange and Act
        boolean result = IsSimplePower.isSimplePower(1, 12);
        // Assert
        assertTrue(result);
    }
    
    @Test
        public void testNothing(){
            IsSimplePower s = new IsSimplePower();
            }
    @Test
    public void testSimplePower_xGreaterThan1_nEquals1() {
        assertFalse(IsSimplePower.isSimplePower(2, 1));
    }
    @Test
    public void testSimplePower_yInitializedToN() {
        assertTrue(IsSimplePower.isSimplePower(16, 2));
    }
    @Test
    public void TestIsSimplePowerNegative_XLessThan1() {
        assertFalse(IsSimplePower.isSimplePower(0,2));
    }
                                    
}