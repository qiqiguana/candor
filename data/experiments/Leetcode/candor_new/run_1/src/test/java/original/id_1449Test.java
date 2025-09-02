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
        int[] cost = {4, 3, 2, 5, 6, 7, 2, 5, 5};
        String expected = "777777";
        Solution1449 solution1449 = new Solution1449();
        assertEquals(expected, solution1449.largestNumber(cost, 12));
    }
}