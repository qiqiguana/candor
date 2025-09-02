package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of XOrY.
*/
class XOrYTest {

    @Test
    void test_xOrY_PrimeNumber_ReturnsX() {
        int result = XOrY.xOrY(7, 34, 12);
        assertEquals(34, result);
    }
}