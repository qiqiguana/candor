package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1897.
*/
class Solution1897Test {
    @Test
    void test_makeEqual() {
        Solution1897 solution = new Solution1897();
        String[] words = {"abc","cab","bca"};
        boolean expected = true;
        boolean actual = solution.makeEqual(words);
        assertEquals(expected,actual);
    }
}