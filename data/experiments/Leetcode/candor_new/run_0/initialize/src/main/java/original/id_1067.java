package original;

import java.util.Arrays;
class Solution1067 {
    private int d;
    private int[] a = new int[11];
    private int[][] dp = new int[11][11];

    public int digitsCount(int d, int low, int high) {
  /**
  Given a single-digit integer d and two integers low and high, return the number of times that d occurs as a digit in all integers in the inclusive range [low, high]. &nbsp; Example 1: Input: d = 1, low = 1, high = 13 Output: 6 Explanation: The digit d = 1 occurs 6 times in 1, 10, 11, 12, 13. Note that the digit d = 1 occurs twice in the number 11. Example 2: Input: d = 3, low = 100, high = 250 Output: 35 Explanation: The digit d = 3 occurs 35 times in 103,113,123,130,131,...,238,239,243. &nbsp; Constraints: 0 &lt;= d &lt;= 9 1 &lt;= low &lt;= high &lt;= 2 * 108
  */
        this.d = d;
        return f(high) - f(low - 1);
    }

    private int f(int n) {
        for (var e : dp) {
            Arrays.fill(e, -1);
        }
        int len = 0;
        while (n > 0) {
            a[++len] = n % 10;
            n /= 10;
        }
        return dfs(len, 0, true, true);
    }

    private int dfs(int pos, int cnt, boolean lead, boolean limit) {
        if (pos <= 0) {
            return cnt;
        }
        if (!lead && !limit && dp[pos][cnt] != -1) {
            return dp[pos][cnt];
        }
        int up = limit ? a[pos] : 9;
        int ans = 0;
        for (int i = 0; i <= up; ++i) {
            if (i == 0 && lead) {
                ans += dfs(pos - 1, cnt, lead, limit && i == up);
            } else {
                ans += dfs(pos - 1, cnt + (i == d ? 1 : 0), false, limit && i == up);
            }
        }
        if (!lead && !limit) {
            dp[pos][cnt] = ans;
        }
        return ans;
    }
}