package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3274.
*/
class Solution3274Test {
    @Test
    void test_checkTwoChessboards_true() {
        Solution3274 solution = new Solution3274();
        String coordinate1 = "A1";
        String coordinate2 = "C3";
        boolean expected = true;
        boolean actual = solution.checkTwoChessboards(coordinate1, coordinate2);
        assertEquals(expected, actual);
    }
}
