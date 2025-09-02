package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0517.
*/
class Solution0517Test {
    @Test
    void testFindMinMoves_ImpossibleCase() {
        // Arrange
        int[] machines = {1, 0, 0};
        Solution0517 solution = new Solution0517();

        // Act and Assert
        assertEquals(-1, solution.findMinMoves(machines));
    }
}