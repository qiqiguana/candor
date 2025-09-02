package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2156.
*/
class Solution2156Test {
    @Test
    void testSubStrHash() {
        // Arrange
        Solution2156 solution = new Solution2156();
        String s = "leetcode";
        int power = 7;
        int modulo = 20;
        int k = 2;
        int hashValue = 0;
        String expected = "ee";

        // Act
        String actual = solution.subStrHash(s, power, modulo, k, hashValue);

        // Assert
        assertEquals(expected, actual);
    }
}