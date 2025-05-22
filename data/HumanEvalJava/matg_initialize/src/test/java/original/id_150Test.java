package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of XOrY.
*/
class XOrYTest {
    @Test
    void test_xOrY_with_prime_number(){
        // Test data with prime number 7
        int n = 7;
        int x = 34;
        int y = 12;
        assertEquals(x, XOrY.xOrY(n,x,y));
    }
}