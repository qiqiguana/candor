package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0942.
*/
class Solution0942Test {

    @Test
    void test_diStringMatch_I_in_input_string() {
        // given
        Solution0942 solution = new Solution0942();
        String s = "ID";
        int[] expected = {0, 2, 1};
        // when
        int[] result = solution.diStringMatch(s);
        // then
        assertArrayEquals(expected, result);
    }
}