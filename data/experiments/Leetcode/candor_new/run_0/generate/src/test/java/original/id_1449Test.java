package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1449.
*/
class Solution1449Test {
    @Test
    void testLargestNumber() {
        int[] cost = {4,3,2,5,6,7,2,5,5};
        int target = 12;
        String expected = "777777";
        Solution1449 solution1449 = new Solution1449();
        String actual = solution1449.largestNumber(cost, target);
        assertEquals(expected, actual);
    }
    @Test
    public void testUncoveredLine29() {
        Solution1449 solution = new Solution1449();
        int[] cost = {2, 4, 6, 2, 4, 6, 4, 4, 4};
        int target = 5;
        String expected = "0";
        String actual = solution.largestNumber(cost, target);
        assertEquals(expected, actual);
    }
}