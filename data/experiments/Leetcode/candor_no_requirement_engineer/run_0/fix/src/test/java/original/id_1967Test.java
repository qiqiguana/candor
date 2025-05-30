package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1967.
*/
class Solution1967Test {
    @Test
    void testNumOfStrings() {
        Solution1967 solution = new Solution1967();
        String[] patterns = {"a", "b", "c"};
        String word = "abc";
        int result = solution.numOfStrings(patterns, word);
        assertEquals(3, result);
    }
}