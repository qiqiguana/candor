package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsSimplePower.
*/
class IsSimplePowerTest {
    @Test
    void isSimplePower_WhenXisOneAndNisGreaterThenOne_ReturnsTrue() {
        // Arrange
        int x = 1;
        int n = 2;
        Boolean expectedResult = true;

        // Act
        Boolean actualResult = IsSimplePower.isSimplePower(x, n);

        // Assert
        assertEquals(expectedResult, actualResult);
    }
    
    @Test
        public void testNothing(){
            IsSimplePower s = new IsSimplePower();
            }
    @Test
    public void testIsSimplePower_x1_n1() {
        assertTrue(IsSimplePower.isSimplePower(1, 1));
    }
    @Test
    public void testIsSimplePower_x1_nGt1() {
        assertTrue(IsSimplePower.isSimplePower(1, 2));
    }
    @Test
    public void testIsSimplePower_x1_nLt0() {
        assertTrue(IsSimplePower.isSimplePower(1, -2));
    }
    @Test
    public void testIsSimplePower_xGt1_n1() {
        assertFalse(IsSimplePower.isSimplePower(2, 1));
    }
    @Test
    public void testIsSimplePower_xPowN() {
        assertTrue(IsSimplePower.isSimplePower(8, 2));
    }
    @Test
    public void testIsSimplePower_xNotPowN() {
        assertFalse(IsSimplePower.isSimplePower(10, 2));
    }
                                    
}