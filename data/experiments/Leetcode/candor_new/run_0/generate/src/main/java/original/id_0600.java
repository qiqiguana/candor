package original;

class Solution0600 {
    private int n;
    private Integer[][] f;

    public int findIntegers(int n) {
  /**
  Given a positive integer n, return the number of the integers in the range [0, n] whose binary representations do not contain consecutive ones. &nbsp; Example 1: Input: n = 5 Output: 5 Explanation: Here are the non-negative integers &lt;= 5 with their corresponding binary representations: 0 : 0 1 : 1 2 : 10 3 : 11 4 : 100 5 : 101 Among them, only integer 3 disobeys the rule (two consecutive ones) and the other 5 satisfy the rule. Example 2: Input: n = 1 Output: 2 Example 3: Input: n = 2 Output: 3 &nbsp; Constraints: 1 &lt;= n &lt;= 109
  */
        this.n = n;
        int m = Integer.SIZE - Integer.numberOfLeadingZeros(n);
        f = new Integer[m][2];
        return dfs(m - 1, 0, true);
    }

    private int dfs(int i, int pre, boolean limit) {
        if (i < 0) {
            return 1;
        }
        if (!limit && f[i][pre] != null) {
            return f[i][pre];
        }
        int up = limit ? (n >> i & 1) : 1;
        int ans = 0;
        for (int j = 0; j <= up; ++j) {
            if (j == 1 && pre == 1) {
                continue;
            }
            ans += dfs(i - 1, j, limit && j == up);
        }
        if (!limit) {
            f[i][pre] = ans;
        }
        return ans;
    }
}