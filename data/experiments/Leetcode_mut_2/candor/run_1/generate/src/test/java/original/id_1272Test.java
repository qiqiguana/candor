package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Solution1272.
*/
class Solution1272Test {
    @Test
    void testRemoveInterval_nonOverlappingIntervals() {
        // Arrange
        Solution1272 solution = new Solution1272();
        int[][] intervals = {{1, 3}, {4, 6}, {7, 9}};
        int[] toBeRemoved = {2, 5};
        List<List<Integer>> expected = Arrays.asList(Arrays.asList(1, 2), Arrays.asList(5, 6), Arrays.asList(7, 9));

        // Act
        List<List<Integer>> actual = solution.removeInterval(intervals, toBeRemoved);

        // Assert
        assertEquals(expected, actual);
    }
}