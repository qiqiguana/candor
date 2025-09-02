package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsEqualToSumEven.
*/
class IsEqualToSumEvenTest {
    @Test
    void isEqualToSumEven_WhenNumberIsLessThan8_ReturnsFalse() {
        assertFalse(IsEqualToSumEven.isEqualToSumEven(4));
    }
}
