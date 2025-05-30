package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1958.
*/
class Solution1958Test {
    @Test
    void testCheckMove_EmptyBoard_ReturnsFalse() {
        // Arrange
        char[][] board = {{'.', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', '.'}};
        Solution1958 solution = new Solution1958();

        // Act and Assert
        assertFalse(solution.checkMove(board, 0, 0, 'B'));
    }
}