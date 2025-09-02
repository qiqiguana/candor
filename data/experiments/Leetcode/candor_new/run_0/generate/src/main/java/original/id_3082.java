package original;

class Solution3082 {
    public int sumOfPower(int[] nums, int k) {
  /**
  You are given an integer array nums of length n and a positive integer k. The power of an array of integers is defined as the number of subsequences with their sum equal to k. Return the sum of power of all subsequences of nums. Since the answer may be very large, return it modulo 109 + 7. &nbsp; Example 1: Input: nums = [1,2,3], k = 3 Output: 6 Explanation: There are 5 subsequences of nums with non-zero power: The subsequence [1,2,3] has 2 subsequences with sum == 3: [1,2,3] and [1,2,3]. The subsequence [1,2,3] has 1 subsequence with sum == 3: [1,2,3]. The subsequence [1,2,3] has 1 subsequence with sum == 3: [1,2,3]. The subsequence [1,2,3] has 1 subsequence with sum == 3: [1,2,3]. The subsequence [1,2,3] has 1 subsequence with sum == 3: [1,2,3]. Hence the answer is 2 + 1 + 1 + 1 + 1 = 6. Example 2: Input: nums = [2,3,3], k = 5 Output: 4 Explanation: There are 3 subsequences of nums with non-zero power: The subsequence [2,3,3] has 2 subsequences with sum == 5: [2,3,3] and [2,3,3]. The subsequence [2,3,3] has 1 subsequence with sum == 5: [2,3,3]. The subsequence [2,3,3] has 1 subsequence with sum == 5: [2,3,3]. Hence the answer is 2 + 1 + 1 = 4. Example 3: Input: nums = [1,2,3], k = 7 Output: 0 Explanation:&nbsp;There exists no subsequence with sum 7. Hence all subsequences of nums have power = 0. &nbsp; Constraints: 1 &lt;= n &lt;= 100 1 &lt;= nums[i] &lt;= 104 1 &lt;= k &lt;= 100
  */
        final int mod = (int) 1e9 + 7;
        int n = nums.length;
        int[][] f = new int[n + 1][k + 1];
        f[0][0] = 1;
        for (int i = 1; i <= n; ++i) {
            for (int j = 0; j <= k; ++j) {
                f[i][j] = (f[i - 1][j] * 2) % mod;
                if (j >= nums[i - 1]) {
                    f[i][j] = (f[i][j] + f[i - 1][j - nums[i - 1]]) % mod;
                }
            }
        }
        return f[n][k];
    }
}