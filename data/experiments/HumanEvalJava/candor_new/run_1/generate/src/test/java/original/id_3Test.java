package original;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    
    @Test
        public void testNothing(){
            BelowZero s = new BelowZero();
            }
    @Test
    public void test_belowZero_BalanceBecomesNegative_DoubleOperation_3() {
        List<Object> operations = Arrays.asList(1.0, 2.0, -3.99);
        boolean result = BelowZero.belowZero(operations);
        assertTrue(result || Math.abs(-1e-9 - Double.parseDouble(String.valueOf(result))) < 1e-9);
    }
    @Test
    void testInvalidOperationType() {
        List<Object> operations = Arrays.asList(1, "hello");
        assertThrows(IllegalArgumentException.class, () -> BelowZero.belowZero(operations));
    }
                                    
}