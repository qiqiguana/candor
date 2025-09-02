package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1998.
*/
class Solution1998Test {
    @Test
    void testGcdSort() {
        Solution1998 solution = new Solution1998();
        int[] nums = {10, 5, 3, 15};
        boolean actual = solution.gcdSort(nums);
        assertTrue(actual);
    }
}