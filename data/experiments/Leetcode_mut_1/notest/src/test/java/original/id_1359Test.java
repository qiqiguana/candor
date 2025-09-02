package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1359.
*/
class Solution1359Test {
    @Test
    void testCountOrdersForSmallN() {
        // Arrange
        Solution1359 solution = new Solution1359();
        int n = 3;
        long expected = 90L;

        // Act
        long actual = solution.countOrders(n);

        // Assert
        assertEquals(expected, actual);
    }
}