package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1952.
*/
class Solution1952Test {
    @Test
    void testIsThree() {
        Solution1952 solution = new Solution1952();
        assertTrue(solution.isThree(4)); // 4 is the smallest number that has exactly one divisor other than 1 and itself
    }
}