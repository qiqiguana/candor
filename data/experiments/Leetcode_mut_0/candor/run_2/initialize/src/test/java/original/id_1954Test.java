package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1954.
*/
class Solution1954Test {

    @Test
    void testMinimumPerimeter_Returns8_WhenNeededApplesIsLessThan12() {
        // Arrange
        Solution1954 solution = new Solution1954();
        long neededApples = 10;

        // Act
        long result = solution.minimumPerimeter(neededApples);

        // Assert
        assertEquals(8, result);
    }
}