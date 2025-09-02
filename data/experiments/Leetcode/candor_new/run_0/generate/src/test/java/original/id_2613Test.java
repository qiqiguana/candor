package original;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Map;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2613.
*/
class Solution2613Test {
    @Test
    void testBeautifulPair() {
        Solution2613 solution = new Solution2613();
        int[] nums1 = {1, 2, 3};
        int[] nums2 = {4, 5, 6};
        int[] expected = {0, 1};
        int[] actual = solution.beautifulPair(nums1, nums2);
        assertArrayEquals(expected, actual);
    }
    @Test
    public void testBeautifulPairCorrectResult1() {
        Solution2613 s = new Solution2613();
        int[] result = s.beautifulPair(new int[]{1,2,3,2,4}, new int[]{2,3,1,2,3});
        assertEquals(0, result[0]);
        assertEquals(3, result[1]);
    }
}