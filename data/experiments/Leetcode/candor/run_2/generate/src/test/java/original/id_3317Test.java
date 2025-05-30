package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3317.
*/
class Solution3317Test {
    @Test
    void test_numberOfWays()
    {
        // Arrange
        Solution3317 solution = new Solution3317();
        int n = 3;
        int x = 2;
        int y = 3;

        // Act
        long actualResult = solution.numberOfWays(n, x, y);

        // Assert
        assertEquals(60L, actualResult);
    }
}
