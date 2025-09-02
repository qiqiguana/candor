package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0387.
*/
class Solution0387Test {
    @Test
    void testFirstUniqChar_SingleUniqueCharacter_ReturnsIndex() {
        // Arrange
        Solution0387 solution = new Solution0387();
        String input = "leetcode";
        int expected = 0;
        
        // Act
        int actual = solution.firstUniqChar(input);
        
        // Assert
        assertEquals(expected, actual);
    }
}
