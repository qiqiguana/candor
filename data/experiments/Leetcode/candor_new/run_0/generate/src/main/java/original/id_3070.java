package original;

class Solution3070 {
    public int countSubmatrices(int[][] grid, int k) {
  /**
  You are given a 0-indexed integer matrix grid and an integer k. Return the number of submatrices that contain the top-left element of the grid, and have a sum less than or equal to k. &nbsp; Example 1: Input: grid = [[7,6,3],[6,6,1]], k = 18 Output: 4 Explanation: There are only 4 submatrices, shown in the image above, that contain the top-left element of grid, and have a sum less than or equal to 18. Example 2: Input: grid = [[7,2,9],[1,5,0],[2,6,6]], k = 20 Output: 6 Explanation: There are only 6 submatrices, shown in the image above, that contain the top-left element of grid, and have a sum less than or equal to 20. &nbsp; Constraints: m == grid.length n == grid[i].length 1 &lt;= n, m &lt;= 1000 0 &lt;= grid[i][j] &lt;= 1000 1 &lt;= k &lt;= 109
  */
        int m = grid.length, n = grid[0].length;
        int[][] s = new int[m + 1][n + 1];
        int ans = 0;
        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                s[i][j] = s[i - 1][j] + s[i][j - 1] - s[i - 1][j - 1] + grid[i - 1][j - 1];
                if (s[i][j] <= k) {
                    ++ans;
                }
            }
        }
        return ans;
    }
}