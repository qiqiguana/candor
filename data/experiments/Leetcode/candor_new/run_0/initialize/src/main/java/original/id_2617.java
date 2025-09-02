package original;

import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Queue;
class Solution2617 {
    public int minimumVisitedCells(int[][] grid) {
  /**
  You are given a 0-indexed m x n integer matrix grid. Your initial position is at the top-left cell (0, 0). Starting from the cell (i, j), you can move to one of the following cells: Cells (i, k) with j &lt; k &lt;= grid[i][j] + j (rightward movement), or Cells (k, j) with i &lt; k &lt;= grid[i][j] + i (downward movement). Return the minimum number of cells you need to visit to reach the bottom-right cell (m - 1, n - 1). If there is no valid path, return -1. &nbsp; Example 1: Input: grid = [[3,4,2,1],[4,2,3,1],[2,1,0,0],[2,4,0,0]] Output: 4 Explanation: The image above shows one of the paths that visits exactly 4 cells. Example 2: Input: grid = [[3,4,2,1],[4,2,1,1],[2,1,1,0],[3,4,1,0]] Output: 3 Explanation: The image above shows one of the paths that visits exactly 3 cells. Example 3: Input: grid = [[2,1,0],[1,0,0]] Output: -1 Explanation: It can be proven that no path exists. &nbsp; Constraints: m == grid.length n == grid[i].length 1 &lt;= m, n &lt;= 105 1 &lt;= m * n &lt;= 105 0 &lt;= grid[i][j] &lt; m * n grid[m - 1][n - 1] == 0
  */
        int m = grid.length, n = grid[0].length;
        int[][] dist = new int[m][n];
        PriorityQueue<int[]>[] row = new PriorityQueue[m];
        PriorityQueue<int[]>[] col = new PriorityQueue[n];
        for (int i = 0; i < m; ++i) {
            Arrays.fill(dist[i], -1);
            row[i] = new PriorityQueue<>((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        }
        for (int i = 0; i < n; ++i) {
            col[i] = new PriorityQueue<>((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        }
        dist[0][0] = 1;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                while (!row[i].isEmpty() && grid[i][row[i].peek()[1]] + row[i].peek()[1] < j) {
                    row[i].poll();
                }
                if (!row[i].isEmpty() && (dist[i][j] == -1 || row[i].peek()[0] + 1 < dist[i][j])) {
                    dist[i][j] = row[i].peek()[0] + 1;
                }
                while (!col[j].isEmpty() && grid[col[j].peek()[1]][j] + col[j].peek()[1] < i) {
                    col[j].poll();
                }
                if (!col[j].isEmpty() && (dist[i][j] == -1 || col[j].peek()[0] + 1 < dist[i][j])) {
                    dist[i][j] = col[j].peek()[0] + 1;
                }
                if (dist[i][j] != -1) {
                    row[i].offer(new int[] {dist[i][j], j});
                    col[j].offer(new int[] {dist[i][j], i});
                }
            }
        }
        return dist[m - 1][n - 1];
    }
}