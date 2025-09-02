
package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Solution1466.
*/
class Solution1466Test {

@Test
void testMinReorder_1() {
    Solution1466 solution = new Solution1466();
    int n = 6;
    int[][] connections = {{0,1},{1,2},{2,3},{4,5}};
    int actual = solution.minReorder(n, connections);
    int expected = 3;
    assertEquals(expected, actual);
}

}