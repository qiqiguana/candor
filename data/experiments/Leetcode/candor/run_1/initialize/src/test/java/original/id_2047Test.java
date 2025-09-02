package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2047.
*/
class Solution2047Test {
    @Test
    void testCountValidWords() {
        Solution2047 solution = new Solution2047();
        String sentence = "cat dog ! a";
        int expected = 4;
        assertEquals(expected, solution.countValidWords(sentence));
    }
}