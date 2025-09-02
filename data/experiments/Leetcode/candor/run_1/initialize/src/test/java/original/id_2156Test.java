package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2156.
*/
class Solution2156Test {
    @Test
    void test_subStrHash() {
        // Arrange
        String s = "fbxzaad";
        int power = 31;
        int modulo = 1000000007;
        int k = 3;
        int hashValue = 32;
        Solution2156 solution2156 = new Solution2156();
        // Act
        String actual = solution2156.subStrHash(s, power, modulo, k, hashValue);
        // Assert
        assertEquals("aad", actual);
    }
}