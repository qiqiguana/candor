package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayDeque;
import java.util.Deque;
/**
* Test class of Solution0739.
*/
class Solution0739Test {
@Test
void testDailyTemperatures() {
    int[] input = {73, 74, 75, 71, 69, 72, 76, 73};
    Solution0739 solution = new Solution0739();
    assertArrayEquals(new int[]{1, 1, 4, 2, 1, 1, 0, 0}, solution.dailyTemperatures(input));
}
}