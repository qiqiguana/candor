package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0418.
*/
class Solution0418Test {
    @Test
    void test_wordsTyping_1() {
        Solution0418 solution = new Solution0418();
        String[] sentence = {"f", "p", "a"};
        int rows = 8;
        int cols = 7;
        int expected = 10;
        int actual = solution.wordsTyping(sentence, rows, cols);
        assertEquals(expected, actual);
    }
}