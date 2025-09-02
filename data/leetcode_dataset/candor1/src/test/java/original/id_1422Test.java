package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1422.
*/
class Solution1422Test {
    @Test
    void testMaxScore()
    {
        // Arrange
        Solution1422 solution = new Solution1422();
        String s = "011101";
        int expected = 5;

        // Act
        int actual = solution.maxScore(s);

        // Assert
        assertEquals(expected, actual);
    }
}