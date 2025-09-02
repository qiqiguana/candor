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
        char[] letters = {'c', 'f', 'j'};
        Solution0744 solution0744 = new Solution0744();
        char actual = solution0744.nextGreatestLetter(letters, 'a');
        char expected = 'c';
        assertEquals(expected, actual);
    }
}
