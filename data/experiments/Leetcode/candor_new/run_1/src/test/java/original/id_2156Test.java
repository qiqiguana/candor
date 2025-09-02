package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2156.
*/
class Solution2156Test {
    @Test
    void testSubStrHash() {
        Solution2156 solution2156 = new Solution2156();
        String result = solution2156.subStrHash("fbxzaad", 31, 1000000007, 3, 32);
        assertEquals("aad", result);
    }
}
