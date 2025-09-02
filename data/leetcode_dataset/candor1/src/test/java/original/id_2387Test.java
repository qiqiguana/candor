package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2387.
*/
class Solution2387Test {

@Test
void matrixMedian() {
Solution2387 solution = new Solution2387();
int[][] grid = {{1, 3, 5}, {2, 6, 9}};
assertEquals(3,solution.matrixMedian(grid));
}
}