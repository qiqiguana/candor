package original;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0914.
*/
class Solution0914Test {
    @Test
    void testHasGroupsSizeX_ReturnsTrue_WhenGCDIsGreaterThanOrEqualTo2() {
        // Arrange
        Solution0914 solution = new Solution0914();
        int[] deck = {1, 1, 1, 2, 2, 2};

        // Act
        boolean result = solution.hasGroupsSizeX(deck);

        // Assert
        assertTrue(result);
    }
}
