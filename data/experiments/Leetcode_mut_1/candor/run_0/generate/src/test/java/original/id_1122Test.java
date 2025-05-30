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
        int[] arr1 = {2, 3, 1, 3, 2, 4, 6, 7, 9, 2, 19};
        int[] arr2 = {2, 1, 4, 3, 9, 6};
        int[] expected = {2, 2, 2, 1, 4, 3, 3, 9, 6, 7, 19};
        assertArrayEquals(expected, solution.relativeSortArray(arr1, arr2));
    }
}