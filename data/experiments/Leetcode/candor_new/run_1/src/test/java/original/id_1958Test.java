package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1958.
*/
class Solution1958Test {
    @Test
    void testCheckMove_ReturnsFalse_WhenNoColorIsFoundInAnyDirection() {
        // Arrange
        char[][] board = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = '.';
            }
        }
        Solution1958 solution = new Solution1958();

        // Act and Assert
        assertFalse(solution.checkMove(board, 3, 3, 'B'));
    }
}