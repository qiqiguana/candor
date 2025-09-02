package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1897.
*/
class Solution1897Test {
    @Test
    void testMakeEqual_DifferentLengthWords_ReturnsTrue() {
        Solution1897 solution = new Solution1897();
        String[] words = {"abc", "cab"};
        boolean result = solution.makeEqual(words);
        assertTrue(result);
    }
}