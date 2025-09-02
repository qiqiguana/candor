package original;

import java.util.HashMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of NeighborSum.
*/
class NeighborSumTest {

    @Test
    void testAdjacentSum() {
        int[][] grid = {{1, 2, 3}, {4, 5, 6}};
        NeighborSum obj = new NeighborSum(grid);
        assertEquals(12, obj.adjacentSum(5));
    }
}
