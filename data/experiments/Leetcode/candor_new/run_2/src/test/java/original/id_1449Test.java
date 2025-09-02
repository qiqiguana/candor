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
        String actual = new Solution1449().largestNumber(cost, target);
        assertEquals(expected, actual);
    }
}