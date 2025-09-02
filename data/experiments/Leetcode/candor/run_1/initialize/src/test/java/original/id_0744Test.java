package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0744.
*/
class Solution0744Test {
    @Test
    void test_nextGreatestLetter() {
        Solution0744 solution = new Solution0744();
        char[] letters = {'c', 'f', 'j'};
        char target = 'a';
        char expected = 'c';
        assertEquals(expected, solution.nextGreatestLetter(letters, target));
    }
}