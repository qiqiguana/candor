package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0517.
*/
class Solution0517Test {
    @Test
    void testFindMinMoves_WhenInputIsValid_ReturnsCorrectResult() {
        // Arrange
        Solution0517 solution = new Solution0517();
        int[] machines = {1, 0, 5};

        // Act
        int result = solution.findMinMoves(machines);

        // Assert
        assertEquals(3, result);
    }
    @Test
    public void testFindMinMoves_ReturnMinusOne_WhenTotalDressesNotDivisibleByNumberOfMachines() {
        // Arrange
        Solution0517 solution = new Solution0517();
        int[] machines = {0, 3, 2};
        int expectedResult = -1;
    
        // Act
        int actualResult = solution.findMinMoves(machines);
    
        // Assert
        assertEquals(expectedResult, actualResult);
    }
}