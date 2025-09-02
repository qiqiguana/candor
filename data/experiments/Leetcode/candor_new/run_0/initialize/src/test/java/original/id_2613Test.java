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
}