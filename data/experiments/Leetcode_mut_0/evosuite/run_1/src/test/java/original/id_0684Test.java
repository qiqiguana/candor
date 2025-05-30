package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0684.
*/
class Solution0684Test {

    @Test
    void findRedundantConnectionTest() {
        Solution0684 solution0684 = new Solution0684();
        int[][] edges = {{1, 2}, {1, 3}, {2, 3}};
        int[] expected = {2, 3};
        assertArrayEquals(expected, solution0684.findRedundantConnection(edges));
    }
}