package original;

import java.util.Arrays;
class Solution1478 {
    public int minDistance(int[] houses, int k) {
  /**
  Given the array houses where houses[i] is the location of the ith house along a street and an integer k, allocate k mailboxes in the street. Return the minimum total distance between each house and its nearest mailbox. The test cases are generated so that the answer fits in a 32-bit integer. &nbsp; Example 1: Input: houses = [1,4,8,10,20], k = 3 Output: 5 Explanation: Allocate mailboxes in position 3, 9 and 20. Minimum total distance from each houses to nearest mailboxes is |3-1| + |4-3| + |9-8| + |10-9| + |20-20| = 5 Example 2: Input: houses = [2,3,5,12,18], k = 2 Output: 9 Explanation: Allocate mailboxes in position 3 and 14. Minimum total distance from each houses to nearest mailboxes is |2-3| + |3-3| + |5-3| + |12-14| + |18-14| = 9. &nbsp; Constraints: 1 &lt;= k &lt;= houses.length &lt;= 100 1 &lt;= houses[i] &lt;= 104 All the integers of houses are unique.
  */
        Arrays.sort(houses);
        int n = houses.length;
        int[][] g = new int[n][n];
        for (int i = n - 2; i >= 0; --i) {
            for (int j = i + 1; j < n; ++j) {
                g[i][j] = g[i + 1][j - 1] + houses[j] - houses[i];
            }
        }
        int[][] f = new int[n][k + 1];
        final int inf = 1 << 30;
        for (int[] e : f) {
            Arrays.fill(e, inf);
        }
        for (int i = 0; i < n; ++i) {
            f[i][1] = g[0][i];
            for (int j = 2; j <= k && j <= i + 1; ++j) {
                for (int p = 0; p < i; ++p) {
                    f[i][j] = Math.min(f[i][j], f[p][j - 1] + g[p + 1][i]);
                }
            }
        }
        return f[n - 1][k];
    }
}