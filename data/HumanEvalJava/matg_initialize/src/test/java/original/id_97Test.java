package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Multiply.
*/
class MultiplyTest {
    @Test
    void testMultiply_ProductOfUnitDigits() {
        assertEquals(16, Multiply.multiply(148, 412));
    }
}