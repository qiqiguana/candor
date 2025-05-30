package original;

import java.util.HashSet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Solution1316.
*/
class Solution1316Test {
    @Test
    void testDistinctEchoSubstrings() {
        Solution1316 solution = new Solution1316();
        String text = "abcabcabc";
        int expected = 3;
        assertEquals(expected, solution.distinctEchoSubstrings(text));
    }
}