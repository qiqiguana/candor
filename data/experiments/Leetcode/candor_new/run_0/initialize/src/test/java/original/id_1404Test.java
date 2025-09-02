package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1404.
*/
class Solution1404Test {
    @Test
    void test_numSteps_withCarry() {
        Solution1404 solution = new Solution1404();
        String input = "110";
        int expected = 4;
        assertEquals(expected, solution.numSteps(input));
    }
}