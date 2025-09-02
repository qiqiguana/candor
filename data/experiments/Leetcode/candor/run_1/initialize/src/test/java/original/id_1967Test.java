package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1967.
*/
class Solution1967Test {
    @Test
    void testNumOfStringsShouldReturnOneWhenWordContainsPattern() {
        Solution1967 solution = new Solution1967();
        String[] patterns = {"a", "b"};
        String word = "ab";
        int expected = 2;
        assertEquals(expected, solution.numOfStrings(patterns, word));
    }
}