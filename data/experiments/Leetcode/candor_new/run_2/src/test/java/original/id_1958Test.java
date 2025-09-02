package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1958.
*/
class Solution1958Test {
    @Test
    void checkMove_test1() {
        Solution1958 solution = new Solution1958();
        char[][] board = {{'B', '.', 'W'}, {'.', 'W', '.'}, {'.', '.', '.'}};
        assertFalse(solution.checkMove(board, 0, 0, 'B'));
    }
}