package original;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2395.
*/
class Solution2395Test {
    @Test
    void testFindSubarrays_DuplicateSum_ReturnsTrue() {
        // Arrange
        Solution2395 solution = new Solution2395();
        int[] nums = {4, 2, 4};

        // Act
        boolean result = solution.findSubarrays(nums);

        // Assert
        assertTrue(result);
    }
}