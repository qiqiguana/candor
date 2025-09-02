package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsEqualToSumEven.
*/
class IsEqualToSumEvenTest {
    @Test
    void testIsEqualSumEven_WhenInput8_ReturnsTrue() {
        Boolean result = IsEqualToSumEven.isEqualToSumEven(8);
        assertTrue(result);
    }
}
