package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsSimplePower.
*/
class IsSimplePowerTest {
    @Test
    void testIsSimplePower_1() {
        assertTrue(IsSimplePower.isSimplePower(16, 2));
    }
    
    @Test
        public void testNothing(){
            IsSimplePower s = new IsSimplePower();
            }
    @Test
    public void Test_Is_Simple_Power_True_For_Base_Case() {
        assertTrue(IsSimplePower.isSimplePower(1, 4));
    }
    @Test
    public void Test_Is_Simple_Power_False_For_Different_Base() {
        assertFalse(IsSimplePower.isSimplePower(2, 3));
    }
    @Test
    public void Test_Is_Simple_Power_True_For_Same_Number() {
        assertTrue(IsSimplePower.isSimplePower(1, 1));
    }
    @Test
    public void Test_Is_Simple_Power_False_For_N_Equals_One() {
        assertFalse(IsSimplePower.isSimplePower(5, 1));
    }
    @Test
    public void Test_Is_Simple_Power_False_For_Non_Integer_Exponent() {
        assertFalse(IsSimplePower.isSimplePower(8, 3));
    }
                                    
}