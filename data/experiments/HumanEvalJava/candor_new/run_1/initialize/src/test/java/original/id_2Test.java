package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TruncateNumber.
*/
class TruncateNumberTest {
    @Test
    void testTruncateNumber() {
        Double number = 123.456;
        Double expectedDecimalPart = 0.456;
        assertEquals(expectedDecimalPart, TruncateNumber.truncateNumber(number));
    }
}
