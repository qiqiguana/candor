package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3386.
*/
class Solution3386Test {
    @Test
    void testButtonWithLongestTime_SingleEvent_ReturnsFirstButton() {
        // Arrange
        int[][] events = {{1, 10}};
        Solution3386 solution = new Solution3386();
        
        // Act
        int result = solution.buttonWithLongestTime(events);
        
        // Assert
        assertEquals(1, result);
    }
}