package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1595.
*/
class Solution1595Test {
    @Test
    void testConnectTwoGroups() {
        List<List<Integer>> cost = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>(Arrays.asList(100, 19, 22, 7));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(95,  6, 20, 5));
        List<Integer> list3 = new ArrayList<>(Arrays.asList(100, 10, 11, 10));
        cost.add(list1);
        cost.add(list2);
        cost.add(list3);
        Solution1595 solution = new Solution1595();
        int result = solution.connectTwoGroups(cost);
        assertEquals(119, result);
    }
}
