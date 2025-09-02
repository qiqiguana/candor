package original;

import java.util.HashMap;

import java.util.Map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3154.
*/
class Solution3154Test {
    @Test
    void testWaysToReachStair() {
        // Arrange
        Solution3154 solution = new Solution3154();

        // Act and Assert
        assertEquals(4, solution.waysToReachStair(2));
    }
}