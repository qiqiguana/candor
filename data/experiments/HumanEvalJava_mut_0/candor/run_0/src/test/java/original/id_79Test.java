package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of DecimalToBinary.
*/
class DecimalToBinaryTest {
    @Test
    void testDecimalToBinary() {
        assertEquals("db0db", DecimalToBinary.decimalToBinary(0));
    }
}