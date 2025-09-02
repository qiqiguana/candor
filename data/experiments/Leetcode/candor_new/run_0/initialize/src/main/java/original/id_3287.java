package original;

class Solution3287 {
    public int maxValue(int[] nums, int k) {
  /**
  You are given an integer array nums and a positive integer k. The value of a sequence seq of size 2 * x is defined as: (seq[0] OR seq[1] OR ... OR seq[x - 1]) XOR (seq[x] OR seq[x + 1] OR ... OR seq[2 * x - 1]). Return the maximum value of any subsequence of nums having size 2 * k. &nbsp; Example 1: Input: nums = [2,6,7], k = 1 Output: 5 Explanation: The subsequence [2, 7] has the maximum value of 2 XOR 7 = 5. Example 2: Input: nums = [4,2,5,6,7], k = 2 Output: 2 Explanation: The subsequence [4, 5, 6, 7] has the maximum value of (4 OR 5) XOR (6 OR 7) = 2. &nbsp; Constraints: 2 &lt;= nums.length &lt;= 400 1 &lt;= nums[i] &lt; 27 1 &lt;= k &lt;= nums.length / 2
  */
        int m = 1 << 7;
        int n = nums.length;
        boolean[][][] f = new boolean[n + 1][k + 2][m];
        f[0][0][0] = true;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                for (int x = 0; x < m; x++) {
                    if (f[i][j][x]) {
                        f[i + 1][j][x] = true;
                        f[i + 1][j + 1][x | nums[i]] = true;
                    }
                }
            }
        }

        boolean[][][] g = new boolean[n + 1][k + 2][m];
        g[n][0][0] = true;

        for (int i = n; i > 0; i--) {
            for (int j = 0; j <= k; j++) {
                for (int y = 0; y < m; y++) {
                    if (g[i][j][y]) {
                        g[i - 1][j][y] = true;
                        g[i - 1][j + 1][y | nums[i - 1]] = true;
                    }
                }
            }
        }

        int ans = 0;

        for (int i = k; i <= n - k; i++) {
            for (int x = 0; x < m; x++) {
                if (f[i][k][x]) {
                    for (int y = 0; y < m; y++) {
                        if (g[i][k][y]) {
                            ans = Math.max(ans, x ^ y);
                        }
                    }
                }
            }
        }

        return ans;
    }
}