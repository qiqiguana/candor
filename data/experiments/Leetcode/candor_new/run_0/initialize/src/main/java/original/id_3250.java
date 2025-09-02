package original;

import java.util.Arrays;
class Solution3250 {
    public int countOfPairs(int[] nums) {
  /**
  You are given an array of positive integers nums of length n. We call a pair of non-negative integer arrays (arr1, arr2) monotonic if: The lengths of both arrays are n. arr1 is monotonically non-decreasing, in other words, arr1[0] &lt;= arr1[1] &lt;= ... &lt;= arr1[n - 1]. arr2 is monotonically non-increasing, in other words, arr2[0] &gt;= arr2[1] &gt;= ... &gt;= arr2[n - 1]. arr1[i] + arr2[i] == nums[i] for all 0 &lt;= i &lt;= n - 1. Return the count of monotonic pairs. Since the answer may be very large, return it modulo 109 + 7. &nbsp; Example 1: Input: nums = [2,3,2] Output: 4 Explanation: The good pairs are: ([0, 1, 1], [2, 2, 1]) ([0, 1, 2], [2, 2, 0]) ([0, 2, 2], [2, 1, 0]) ([1, 2, 2], [1, 1, 0]) Example 2: Input: nums = [5,5,5,5] Output: 126 &nbsp; Constraints: 1 &lt;= n == nums.length &lt;= 2000 1 &lt;= nums[i] &lt;= 50
  */
        final int mod = (int) 1e9 + 7;
        int n = nums.length;
        int m = Arrays.stream(nums).max().getAsInt();
        int[][] f = new int[n][m + 1];
        for (int j = 0; j <= nums[0]; ++j) {
            f[0][j] = 1;
        }
        int[] g = new int[m + 1];
        for (int i = 1; i < n; ++i) {
            g[0] = f[i - 1][0];
            for (int j = 1; j <= m; ++j) {
                g[j] = (g[j - 1] + f[i - 1][j]) % mod;
            }
            for (int j = 0; j <= nums[i]; ++j) {
                int k = Math.min(j, j + nums[i - 1] - nums[i]);
                if (k >= 0) {
                    f[i][j] = g[k];
                }
            }
        }
        int ans = 0;
        for (int j = 0; j <= nums[n - 1]; ++j) {
            ans = (ans + f[n - 1][j]) % mod;
        }
        return ans;
    }
}