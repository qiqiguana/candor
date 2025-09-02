package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2141.
*/
class Solution2141Test {

    @Test
    void test_maxRunTime_ReturnsTheMaximumRunningTime() {
        // Arrange
        Solution2141 solution = new Solution2141();
        int n = 2;
        int[] batteries = {3, 3};

        // Act
        long result = solution.maxRunTime(n, batteries);

        // Assert
        assertEquals(3, result);
    }
}
