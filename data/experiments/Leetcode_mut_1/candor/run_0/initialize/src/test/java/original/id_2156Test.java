package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2156.
*/
class Solution2156Test {
    @Test
    void testSubStrHash_KIsEqualToLengthOfS_ReturnsEntireString() {
        Solution2156 solution = new Solution2156();
        String s = "fbxzaad";
        int power = 31;
        int modulo = 1000000007;
        int k = s.length();
        int hashValue = 32;
        String expected = s;
        assertEquals(expected, solution.subStrHash(s, power, modulo, k, hashValue));
    }
}