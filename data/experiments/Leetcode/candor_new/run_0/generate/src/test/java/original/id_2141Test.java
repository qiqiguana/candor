package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2141.
*/
class Solution2141Test {
    @Test
    void testMaxRunTime_BasicScenario() {
        // Arrange
        Solution2141 solution = new Solution2141();
        int n = 2;
        int[] batteries = {3,3};
        long expectedOutput = 3;

        // Act
        long actualOutput = solution.maxRunTime(n,batteries);

        // Assert
        assertEquals(expectedOutput,actualOutput);
    }
}