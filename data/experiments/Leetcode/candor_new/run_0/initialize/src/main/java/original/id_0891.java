package original;

import java.util.Arrays;
class Solution0891 {
    private static final int MOD = (int) 1e9 + 7;

    public int sumSubseqWidths(int[] nums) {
  /**
  The width of a sequence is the difference between the maximum and minimum elements in the sequence. Given an array of integers nums, return the sum of the widths of all the non-empty subsequences of nums. Since the answer may be very large, return it modulo 109 + 7. A subsequence is a sequence that can be derived from an array by deleting some or no elements without changing the order of the remaining elements. For example, [3,6,2,7] is a subsequence of the array [0,3,1,6,2,2,7]. &nbsp; Example 1: Input: nums = [2,1,3] Output: 6 Explanation: The subsequences are [1], [2], [3], [2,1], [2,3], [1,3], [2,1,3]. The corresponding widths are 0, 0, 0, 1, 1, 2, 2. The sum of these widths is 6. Example 2: Input: nums = [2] Output: 0 &nbsp; Constraints: 1 &lt;= nums.length &lt;= 105 1 &lt;= nums[i] &lt;= 105
  */
        Arrays.sort(nums);
        long ans = 0, p = 1;
        int n = nums.length;
        for (int i = 0; i < n; ++i) {
            ans = (ans + (nums[i] - nums[n - i - 1]) * p + MOD) % MOD;
            p = (p << 1) % MOD;
        }
        return (int) ans;
    }
}