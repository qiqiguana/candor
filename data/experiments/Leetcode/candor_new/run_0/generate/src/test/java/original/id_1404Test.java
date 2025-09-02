package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @Test
    public void testNumSteps() {
        Solution1404 s = new Solution1404();
        int result = s.numSteps("1101");
        assertEquals(6, result);
    }
    @Test
    public void testCarryFlagNotSetAfterLoop() {
      Solution1404 solution = new Solution1404();
      String s = "10";
      int ans = solution.numSteps(s);
      assertEquals(1, ans);
    }
}