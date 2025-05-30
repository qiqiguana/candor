package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0387.
*/
class Solution0387Test {
    @Test
    void testFirstUniqChar_SingleCharacterString_ReturnsZero() {
        // Arrange
        Solution0387 solution = new Solution0387();
        String input = "a";
        int expectedResult = 0;

        // Act
        int actualResult = solution.firstUniqChar(input);

        // Assert
        assertEquals(expectedResult, actualResult);
    }
}