package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2156.
*/
class Solution2156Test {
    @Test
    void testSubStrHash() {
        Solution2156 solution = new Solution2156();
        String result = solution.subStrHash("fbxzaad", 31, 1000000021, 3, 32);
        assertEquals("aad", result);
    }
    @Test
    public void testSubStrHashMatchesHashValue() {
        Solution2156 s = new Solution2156();
        String result = s.subStrHash("fbxzaad", 31, 100, 3, 32);
        assertEquals("fbx", result);
    }
}