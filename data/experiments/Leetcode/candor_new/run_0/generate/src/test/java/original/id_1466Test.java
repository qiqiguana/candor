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
    void testMinReorder() {
        Solution1466 solution = new Solution1466();
        int n = 3;
        int[][] connections = {{0,1},{1,2}};
        assertEquals(2, solution.minReorder(n, connections));
    }
}