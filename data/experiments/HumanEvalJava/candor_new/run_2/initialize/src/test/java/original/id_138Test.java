package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsEqualToSumEven.
*/
class IsEqualToSumEvenTest {
    @Test
    void isEqualToSumEven_WhenNumberIs8_ReturnsTrue() {
        // Arrange and Act
        Boolean result = IsEqualToSumEven.isEqualToSumEven(8);
        // Assert
        assertTrue(result);
    }
}
