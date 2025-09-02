package original;

class Solution2912 {
    public int numberOfWays(int n, int m, int k, int[] source, int[] dest) {
  /**
  You are given two integers n and m which represent the size of a 1-indexed grid. You are also given an integer k, a 1-indexed integer array source and a 1-indexed integer array dest, where source and dest are in the form [x, y] representing a cell on the given grid. You can move through the grid in the following way: You can go from cell [x1, y1] to cell [x2, y2] if either x1 == x2 or y1 == y2. Note that you can&#39;t move to the cell you are already in e.g. x1 == x2 and y1 == y2. Return the number of ways you can reach dest from source by moving through the grid exactly k times. Since the answer may be very large, return it modulo 109 + 7. &nbsp; Example 1: Input: n = 3, m = 2, k = 2, source = [1,1], dest = [2,2] Output: 2 Explanation: There are 2 possible sequences of reaching [2,2] from [1,1]: - [1,1] -&gt; [1,2] -&gt; [2,2] - [1,1] -&gt; [2,1] -&gt; [2,2] Example 2: Input: n = 3, m = 4, k = 3, source = [1,2], dest = [2,3] Output: 9 Explanation: There are 9 possible sequences of reaching [2,3] from [1,2]: - [1,2] -&gt; [1,1] -&gt; [1,3] -&gt; [2,3] - [1,2] -&gt; [1,1] -&gt; [2,1] -&gt; [2,3] - [1,2] -&gt; [1,3] -&gt; [3,3] -&gt; [2,3] - [1,2] -&gt; [1,4] -&gt; [1,3] -&gt; [2,3] - [1,2] -&gt; [1,4] -&gt; [2,4] -&gt; [2,3] - [1,2] -&gt; [2,2] -&gt; [2,1] -&gt; [2,3] - [1,2] -&gt; [2,2] -&gt; [2,4] -&gt; [2,3] - [1,2] -&gt; [3,2] -&gt; [2,2] -&gt; [2,3] - [1,2] -&gt; [3,2] -&gt; [3,3] -&gt; [2,3] &nbsp; Constraints: 2 &lt;= n, m &lt;= 109 1 &lt;= k&nbsp;&lt;= 105 source.length == dest.length == 2 1 &lt;= source[1], dest[1] &lt;= n 1 &lt;= source[2], dest[2] &lt;= m
  */
        final int mod = 1000000007;
        long[] f = new long[4];
        f[0] = 1;
        while (k-- > 0) {
            long[] g = new long[4];
            g[0] = ((n - 1) * f[1] + (m - 1) * f[2]) % mod;
            g[1] = (f[0] + (n - 2) * f[1] + (m - 1) * f[3]) % mod;
            g[2] = (f[0] + (m - 2) * f[2] + (n - 1) * f[3]) % mod;
            g[3] = (f[1] + f[2] + (n - 2) * f[3] + (m - 2) * f[3]) % mod;
            f = g;
        }
        if (source[0] == dest[0]) {
            return source[1] == dest[1] ? (int) f[0] : (int) f[2];
        }
        return source[1] == dest[1] ? (int) f[1] : (int) f[3];
    }
}