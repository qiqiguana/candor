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
        Double expectedResult = 0.456;
        Double actualResult = TruncateNumber.truncateNumber(number);
        assertEquals(expectedResult, actualResult);
    }
}