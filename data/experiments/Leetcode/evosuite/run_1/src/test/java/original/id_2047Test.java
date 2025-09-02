package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2047.
*/
class Solution2047Test {
    @Test
    void testCountValidWords_SingleWord_ReturnsOne() {
        Solution2047 solution = new Solution2047();
        String sentence = "hello";
        int expected = 1;
        int actual = solution.countValidWords(sentence);
        assertEquals(expected, actual);
    }
}