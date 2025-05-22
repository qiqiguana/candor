package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of DecimalToBinary.
*/
class DecimalToBinaryTest {
    @Test
    void testDecimalToBinary_ConvertsDecimalNumberToBinary() {
        String result = DecimalToBinary.decimalToBinary(15);
        assertEquals("db1111db", result);
    }
}