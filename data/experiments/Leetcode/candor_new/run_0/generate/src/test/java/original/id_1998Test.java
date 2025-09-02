package original;

import java.util.Arrays;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Map;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1998.
*/
class Solution1998Test {
    @Test
    void testGcdSort() {
        Solution1998 solution = new Solution1998();
        int[] nums = {7, 10, 12};
        assertTrue(solution.gcdSort(nums));
    }
    @Test
    public void testGcdSortTrue1() {
        Solution1998 s = new Solution1998();
        assertTrue(s.gcdSort(new int[] {10,5,9,3,15}));
    }
    @Test
    public void testGcdSortUnsortableArray() {
        Solution1998 solution = new Solution1998();
        int[] nums = {5, 2, 6, 2};
        assertFalse(solution.gcdSort(nums));
    }
}