package original;

class Solution3317 {
    public int numberOfWays(int n, int x, int y) {
  /**
  You are given three integers n, x, and y. An event is being held for n performers. When a performer arrives, they are assigned to one of the x stages. All performers assigned to the same stage will perform together as a band, though some stages might remain empty. After all performances are completed, the jury will award each band a score in the range [1, y]. Return the total number of possible ways the event can take place. Since the answer may be very large, return it modulo 109 + 7. Note that two events are considered to have been held differently if either of the following conditions is satisfied: Any performer is assigned a different stage. Any band is awarded a different score. &nbsp; Example 1: Input: n = 1, x = 2, y = 3 Output: 6 Explanation: There are 2 ways to assign a stage to the performer. The jury can award a score of either 1, 2, or 3 to the only band. Example 2: Input: n = 5, x = 2, y = 1 Output: 32 Explanation: Each performer will be assigned either stage 1 or stage 2. All bands will be awarded a score of 1. Example 3: Input: n = 3, x = 3, y = 4 Output: 684 &nbsp; Constraints: 1 &lt;= n, x, y &lt;= 1000
  */
        final int mod = (int) 1e9 + 7;
        long[][] f = new long[n + 1][x + 1];
        f[0][0] = 1;
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= x; ++j) {
                f[i][j] = (f[i - 1][j] * j % mod + f[i - 1][j - 1] * (x - (j - 1) % mod)) % mod;
            }
        }
        long ans = 0, p = 1;
        for (int j = 1; j <= x; ++j) {
            p = p * y % mod;
            ans = (ans + f[n][j] * p) % mod;
        }
        return (int) ans;
    }
}