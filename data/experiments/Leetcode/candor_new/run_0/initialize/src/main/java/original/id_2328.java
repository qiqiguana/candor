package original;

class Solution2328 {
    private int[][] f;
    private int[][] grid;
    private int m;
    private int n;
    private final int mod = (int) 1e9 + 7;

    public int countPaths(int[][] grid) {
  /**
  You are given an m x n integer matrix grid, where you can move from a cell to any adjacent cell in all 4 directions. Return the number of strictly increasing paths in the grid such that you can start from any cell and end at any cell. Since the answer may be very large, return it modulo 109 + 7. Two paths are considered different if they do not have exactly the same sequence of visited cells. &nbsp; Example 1: Input: grid = [[1,1],[3,4]] Output: 8 Explanation: The strictly increasing paths are: - Paths with length 1: [1], [1], [3], [4]. - Paths with length 2: [1 -&gt; 3], [1 -&gt; 4], [3 -&gt; 4]. - Paths with length 3: [1 -&gt; 3 -&gt; 4]. The total number of paths is 4 + 3 + 1 = 8. Example 2: Input: grid = [[1],[2]] Output: 3 Explanation: The strictly increasing paths are: - Paths with length 1: [1], [2]. - Paths with length 2: [1 -&gt; 2]. The total number of paths is 2 + 1 = 3. &nbsp; Constraints: m == grid.length n == grid[i].length 1 &lt;= m, n &lt;= 1000 1 &lt;= m * n &lt;= 105 1 &lt;= grid[i][j] &lt;= 105
  */
        m = grid.length;
        n = grid[0].length;
        this.grid = grid;
        f = new int[m][n];
        int ans = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                ans = (ans + dfs(i, j)) % mod;
            }
        }
        return ans;
    }

    private int dfs(int i, int j) {
        if (f[i][j] != 0) {
            return f[i][j];
        }
        int ans = 1;
        int[] dirs = {-1, 0, 1, 0, -1};
        for (int k = 0; k < 4; ++k) {
            int x = i + dirs[k], y = j + dirs[k + 1];
            if (x >= 0 && x < m && y >= 0 && y < n && grid[i][j] < grid[x][y]) {
                ans = (ans + dfs(x, y)) % mod;
            }
        }
        return f[i][j] = ans;
    }
}