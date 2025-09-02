package original;

import java.util.Arrays;
class Solution0265 {
    public int minCostII(int[][] costs) {
  /**
  There are a row of n houses, each house can be painted with one of the k colors. The cost of painting each house with a certain color is different. You have to paint all the houses such that no two adjacent houses have the same color. The cost of painting each house with a certain color is represented by an n x k cost matrix costs. For example, costs[0][0] is the cost of painting house 0 with color 0; costs[1][2] is the cost of painting house 1 with color 2, and so on... Return the minimum cost to paint all houses. &nbsp; Example 1: Input: costs = [[1,5,3],[2,9,4]] Output: 5 Explanation: Paint house 0 into color 0, paint house 1 into color 2. Minimum cost: 1 + 4 = 5; Or paint house 0 into color 2, paint house 1 into color 0. Minimum cost: 3 + 2 = 5. Example 2: Input: costs = [[1,3],[2,4]] Output: 5 &nbsp; Constraints: costs.length == n costs[i].length == k 1 &lt;= n &lt;= 100 2 &lt;= k &lt;= 20 1 &lt;= costs[i][j] &lt;= 20 &nbsp; Follow up: Could you solve it in O(nk) runtime?
  */
        int n = costs.length, k = costs[0].length;
        int[] f = costs[0].clone();
        for (int i = 1; i < n; ++i) {
            int[] g = costs[i].clone();
            for (int j = 0; j < k; ++j) {
                int t = Integer.MAX_VALUE;
                for (int h = 0; h < k; ++h) {
                    if (h != j) {
                        t = Math.min(t, f[h]);
                    }
                }
                g[j] += t;
            }
            f = g;
        }
        return Arrays.stream(f).min().getAsInt();
    }
}