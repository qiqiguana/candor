package original;

import java.util.ArrayList;
import java.util.List;
class Solution1102 {
    private int[] p;

    public int maximumMinimumPath(int[][] grid) {
  /**
  Given an m x n integer matrix grid, return the maximum score of a path starting at (0, 0) and ending at (m - 1, n - 1) moving in the 4 cardinal directions. The score of a path is the minimum value in that path. For example, the score of the path 8 &rarr; 4 &rarr; 5 &rarr; 9 is 4. &nbsp; Example 1: Input: grid = [[5,4,5],[1,2,6],[7,4,6]] Output: 4 Explanation: The path with the maximum score is highlighted in yellow. Example 2: Input: grid = [[2,2,1,2,2,2],[1,2,2,2,1,2]] Output: 2 Example 3: Input: grid = [[3,4,6,3,4],[0,2,1,1,7],[8,8,3,2,7],[3,2,4,9,8],[4,1,2,0,0],[4,6,5,4,3]] Output: 3 &nbsp; Constraints: m == grid.length n == grid[i].length 1 &lt;= m, n &lt;= 100 0 &lt;= grid[i][j] &lt;= 109
  */
        int m = grid.length, n = grid[0].length;
        p = new int[m * n];
        List<int[]> q = new ArrayList<>();
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                q.add(new int[] {grid[i][j], i, j});
                p[i * n + j] = i * n + j;
            }
        }
        q.sort((a, b) -> b[0] - a[0]);
        boolean[][] vis = new boolean[m][n];
        int[] dirs = {-1, 0, 1, 0, -1};
        int ans = 0;
        for (int i = 0; find(0) != find(m * n - 1); ++i) {
            int[] t = q.get(i);
            vis[t[1]][t[2]] = true;
            ans = t[0];
            for (int k = 0; k < 4; ++k) {
                int x = t[1] + dirs[k], y = t[2] + dirs[k + 1];
                if (x >= 0 && x < m && y >= 0 && y < n && vis[x][y]) {
                    p[find(x * n + y)] = find(t[1] * n + t[2]);
                }
            }
        }
        return ans;
    }

    private int find(int x) {
        if (p[x] != x) {
            p[x] = find(p[x]);
        }
        return p[x];
    }
}