package original;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Solution1122.
*/
class Solution1122Test {

    @Test
    void testRelativeSortArray() {
        Solution1122 solution = new Solution1122();
        int[] arr1 = {2, 1, 2, 5, 7, 1, 9};
        int[] arr2 = {2, 1, 8};
        int[] expected = {2, 2, 1, 1, 5, 7, 9};
        assertArrayEquals(expected, solution.relativeSortArray(arr1, arr2));
    }

}