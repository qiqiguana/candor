package original;

import java.util.HashMap;

import java.util.HashSet;

import java.util.Map;

import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1207.
*/
class Solution1207Test {

    @Test
    void testUniqueOccurrences_SimpleCase() {
        // Arrange
        Solution1207 solution = new Solution1207();
        int[] arr = {1, 2, 2, 1, 1, 3};
        boolean expectedResult = true;

        // Act
        boolean actualResult = solution.uniqueOccurrences(arr);

        // Assert
        assertEquals(expectedResult, actualResult);
    }
}