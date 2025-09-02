package original;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of BelowZero.
*/
class BelowZeroTest {
    @Test
    void testBelowZero_ReturnsFalse_WhenOperationsDoNotCauseNegativeBalance() {
        // Arrange
        List<Object> operations = new ArrayList<>();
        operations.add(1);
        operations.add(2);
        operations.add(3);

        // Act
        Boolean result = BelowZero.belowZero(operations);

        // Assert
        assertFalse(result);
    }
}