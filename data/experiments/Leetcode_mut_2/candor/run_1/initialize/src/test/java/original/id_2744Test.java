package original;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2744.
*/
class Solution2744Test {
    @Test
    void testMaximumNumberOfStringPairs2() {
        Solution2744 solution = new Solution2744();
        String[] words = {"dc", "cd", "ll", "uu", "ss", "pp", "dd"};
        int expected = 1;
        int actual = solution.maximumNumberOfStringPairs(words);
        assertEquals(expected, actual);
    }
}