package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Modp.
*/
class ModpTest {
    @Test
    void test_modp_with_zero_exponent() {
        // When the exponent is 0, the result should always be 1
        int result = Modp.modp(0, 101);
        assertEquals(1, result);
    }
    
    @Test
        public void testNothing(){
            Modp s = new Modp();
            }
    @Test
    public void test_modp_positive_integer_power_of_2() {
        int n = 3;
        int p = 5;
        assertEquals(3, Modp.modp(n, p));
    }
    @Test
    public void test_modp_zero_power_of_2() {
        int n = 0;
        int p = 101;
        assertEquals(1, Modp.modp(n, p));
    }
    @Test
    public void test_modp_positive_integer_power_of_non_2() {
        int n = 3;
        int p = 11;
        assertEquals(8, Modp.modp(n, p));
    }
    @Test
    public void test_modp_edge_case_1() {
        int n = 1;
        int p = 2;
        assertEquals(0, Modp.modp(n, p));
    }
    @Test
    public void test_modp_positive_integer_power_of_2_2() {
        int n = 4;
        int p = 5;
        assertEquals(1, Modp.modp(n, p));
    }
                                    
}