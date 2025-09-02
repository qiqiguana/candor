package original;

class Solution1955 {
    public int countSpecialSubsequences(int[] nums) {
  /**
  A sequence is special if it consists of a positive number of 0s, followed by a positive number of 1s, then a positive number of 2s. For example, [0,1,2] and [0,0,1,1,1,2] are special. In contrast, [2,1,0], [1], and [0,1,2,0] are not special. Given an array nums (consisting of only integers 0, 1, and 2), return the number of different subsequences that are special. Since the answer may be very large, return it modulo 109 + 7. A subsequence of an array is a sequence that can be derived from the array by deleting some or no elements without changing the order of the remaining elements. Two subsequences are different if the set of indices chosen are different. &nbsp; Example 1: Input: nums = [0,1,2,2] Output: 3 Explanation: The special subsequences are bolded [0,1,2,2], [0,1,2,2], and [0,1,2,2]. Example 2: Input: nums = [2,2,0,0] Output: 0 Explanation: There are no special subsequences in [2,2,0,0]. Example 3: Input: nums = [0,1,2,0,1,2] Output: 7 Explanation: The special subsequences are bolded: - [0,1,2,0,1,2] - [0,1,2,0,1,2] - [0,1,2,0,1,2] - [0,1,2,0,1,2] - [0,1,2,0,1,2] - [0,1,2,0,1,2] - [0,1,2,0,1,2] &nbsp; Constraints: 1 &lt;= nums.length &lt;= 105 0 &lt;= nums[i] &lt;= 2
  */
        final int mod = (int) 1e9 + 7;
        int n = nums.length;
        int[][] f = new int[n][3];
        f[0][0] = nums[0] == 0 ? 1 : 0;
        for (int i = 1; i < n; ++i) {
            if (nums[i] == 0) {
                f[i][0] = (2 * f[i - 1][0] % mod + 1) % mod;
                f[i][1] = f[i - 1][1];
                f[i][2] = f[i - 1][2];
            } else if (nums[i] == 1) {
                f[i][0] = f[i - 1][0];
                f[i][1] = (f[i - 1][0] + 2 * f[i - 1][1] % mod) % mod;
                f[i][2] = f[i - 1][2];
            } else {
                f[i][0] = f[i - 1][0];
                f[i][1] = f[i - 1][1];
                f[i][2] = (f[i - 1][1] + 2 * f[i - 1][2] % mod) % mod;
            }
        }
        return f[n - 1][2];
    }
}