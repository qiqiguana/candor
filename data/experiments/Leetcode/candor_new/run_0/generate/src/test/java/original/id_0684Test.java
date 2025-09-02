package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0684.
*/
class Solution0684Test {
    @Test
    void testFindRedundantConnection() {
        Solution0684 solution = new Solution0684();
        int[][] edges = {{1, 2}, {1, 3}, {2, 3}};
        assertArrayEquals(new int[]{2, 3}, solution.findRedundantConnection(edges));
    }
}