package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of XOrY.
*/
class XOrYTest {
    @Test
    void testXOrY_PrimeNumber_ReturnsX() {
        assertEquals(34, XOrY.xOrY(7, 34, 12));
    }
}