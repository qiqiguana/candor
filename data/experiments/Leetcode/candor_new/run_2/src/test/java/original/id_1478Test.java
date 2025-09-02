package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1478.
*/
class Solution1478Test {

    @Test
    void testMinDistance() {
        // Arrange
        Solution1478 solution = new Solution1478();
        int[] houses = {1, 4, 8, 10, 20};
        int k = 3;
        // Act
        int actualResult = solution.minDistance(houses, k);
        // Assert
        assertEquals(5, actualResult);
    }
}