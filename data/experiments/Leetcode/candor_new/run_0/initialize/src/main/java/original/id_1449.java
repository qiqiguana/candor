package original;

import java.util.Arrays;
class Solution1449 {
    public String largestNumber(int[] cost, int target) {
  /**
  Given an array of integers cost and an integer target, return the maximum integer you can paint under the following rules: The cost of painting a digit (i + 1) is given by cost[i] (0-indexed). The total cost used must be equal to target. The integer does not have 0 digits. Since the answer may be very large, return it as a string. If there is no way to paint any integer given the condition, return &quot;0&quot;. &nbsp; Example 1: Input: cost = [4,3,2,5,6,7,2,5,5], target = 9 Output: &quot;7772&quot; Explanation: The cost to paint the digit &#39;7&#39; is 2, and the digit &#39;2&#39; is 3. Then cost(&quot;7772&quot;) = 2*3+ 3*1 = 9. You could also paint &quot;977&quot;, but &quot;7772&quot; is the largest number. Digit cost 1 -&gt; 4 2 -&gt; 3 3 -&gt; 2 4 -&gt; 5 5 -&gt; 6 6 -&gt; 7 7 -&gt; 2 8 -&gt; 5 9 -&gt; 5 Example 2: Input: cost = [7,6,5,5,5,6,8,7,8], target = 12 Output: &quot;85&quot; Explanation: The cost to paint the digit &#39;8&#39; is 7, and the digit &#39;5&#39; is 5. Then cost(&quot;85&quot;) = 7 + 5 = 12. Example 3: Input: cost = [2,4,6,2,4,6,4,4,4], target = 5 Output: &quot;0&quot; Explanation: It is impossible to paint any integer with total cost equal to target. &nbsp; Constraints: cost.length == 9 1 &lt;= cost[i], target &lt;= 5000
  */
        final int inf = 1 << 30;
        int[][] f = new int[10][target + 1];
        int[][] g = new int[10][target + 1];
        for (var e : f) {
            Arrays.fill(e, -inf);
        }
        f[0][0] = 0;
        for (int i = 1; i <= 9; ++i) {
            int c = cost[i - 1];
            for (int j = 0; j <= target; ++j) {
                if (j < c || f[i][j - c] + 1 < f[i - 1][j]) {
                    f[i][j] = f[i - 1][j];
                    g[i][j] = j;
                } else {
                    f[i][j] = f[i][j - c] + 1;
                    g[i][j] = j - c;
                }
            }
        }
        if (f[9][target] < 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 9, j = target; i > 0;) {
            if (j == g[i][j]) {
                --i;
            } else {
                sb.append(i);
                j = g[i][j];
            }
        }
        return sb.toString();
    }
}