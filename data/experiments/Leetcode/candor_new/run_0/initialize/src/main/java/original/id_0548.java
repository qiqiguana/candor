package original;

import java.util.HashSet;
import java.util.Set;
class Solution0548 {
    public boolean splitArray(int[] nums) {
  /**
  Given an integer array nums of length n, return true if there is a triplet (i, j, k) which satisfies the following conditions: 0 &lt; i, i + 1 &lt; j, j + 1 &lt; k &lt; n - 1 The sum of subarrays (0, i - 1), (i + 1, j - 1), (j + 1, k - 1) and (k + 1, n - 1) is equal. A subarray (l, r) represents a slice of the original array starting from the element indexed l to the element indexed r. &nbsp; Example 1: Input: nums = [1,2,1,2,1,2,1] Output: true Explanation: i = 1, j = 3, k = 5. sum(0, i - 1) = sum(0, 0) = 1 sum(i + 1, j - 1) = sum(2, 2) = 1 sum(j + 1, k - 1) = sum(4, 4) = 1 sum(k + 1, n - 1) = sum(6, 6) = 1 Example 2: Input: nums = [1,2,1,2,1,2,1,2] Output: false &nbsp; Constraints: n ==&nbsp;nums.length 1 &lt;= n &lt;= 2000 -106 &lt;= nums[i] &lt;= 106
  */
        int n = nums.length;
        int[] s = new int[n + 1];
        for (int i = 0; i < n; ++i) {
            s[i + 1] = s[i] + nums[i];
        }
        for (int j = 3; j < n - 3; ++j) {
            Set<Integer> seen = new HashSet<>();
            for (int i = 1; i < j - 1; ++i) {
                if (s[i] == s[j] - s[i + 1]) {
                    seen.add(s[i]);
                }
            }
            for (int k = j + 2; k < n - 1; ++k) {
                if (s[n] - s[k + 1] == s[k] - s[j + 1] && seen.contains(s[n] - s[k + 1])) {
                    return true;
                }
            }
        }
        return false;
    }
}