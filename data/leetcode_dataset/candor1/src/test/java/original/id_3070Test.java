package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3070.
*/
class Solution3070Test {
    @Test
    void test_countSubmatrices() {
        // Arrange
        int[][] grid = {{1, 2}, {3, 4}};
        int k = 5;
        int expected = 3;
        Solution3070 solution = new Solution3070();

        // Act
        int actual = solution.countSubmatrices(grid, k);

        // Assert
        assertEquals(expected, actual);
    }
}