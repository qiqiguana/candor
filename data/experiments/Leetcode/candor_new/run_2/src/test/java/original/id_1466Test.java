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
    void testMinReorder_2() {
        Solution1466 solution = new Solution1466();
        int[][] connections = {{0, 1}, {1, 0}};
        assertEquals(1, solution.minReorder(2, connections));
    }
}