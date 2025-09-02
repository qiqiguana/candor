package original;

import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Queue;
class Solution2577 {
    public int minimumTime(int[][] grid) {
  /**
  You are given a m x n matrix grid consisting of non-negative integers where grid[row][col] represents the minimum time required to be able to visit the cell (row, col), which means you can visit the cell (row, col) only when the time you visit it is greater than or equal to grid[row][col]. You are standing in the top-left cell of the matrix in the 0th second, and you must move to any adjacent cell in the four directions: up, down, left, and right. Each move you make takes 1 second. Return the minimum time required in which you can visit the bottom-right cell of the matrix. If you cannot visit the bottom-right cell, then return -1. &nbsp; Example 1: Input: grid = [[0,1,3,2],[5,1,2,5],[4,3,8,6]] Output: 7 Explanation: One of the paths that we can take is the following: - at t = 0, we are on the cell (0,0). - at t = 1, we move to the cell (0,1). It is possible because grid[0][1] &lt;= 1. - at t = 2, we move to the cell (1,1). It is possible because grid[1][1] &lt;= 2. - at t = 3, we move to the cell (1,2). It is possible because grid[1][2] &lt;= 3. - at t = 4, we move to the cell (1,1). It is possible because grid[1][1] &lt;= 4. - at t = 5, we move to the cell (1,2). It is possible because grid[1][2] &lt;= 5. - at t = 6, we move to the cell (1,3). It is possible because grid[1][3] &lt;= 6. - at t = 7, we move to the cell (2,3). It is possible because grid[2][3] &lt;= 7. The final time is 7. It can be shown that it is the minimum time possible. Example 2: Input: grid = [[0,2,4],[3,2,1],[1,0,4]] Output: -1 Explanation: There is no path from the top left to the bottom-right cell. &nbsp; Constraints: m == grid.length n == grid[i].length 2 &lt;= m, n &lt;= 1000 4 &lt;= m * n &lt;= 105 0 &lt;= grid[i][j] &lt;= 105 grid[0][0] == 0 &nbsp; .spoilerbutton {display:block; border:dashed; padding: 0px 0px; margin:10px 0px; font-size:150%; font-weight: bold; color:#000000; background-color:cyan; outline:0; } .spoiler {overflow:hidden;} .spoiler > div {-webkit-transition: all 0s ease;-moz-transition: margin 0s ease;-o-transition: all 0s ease;transition: margin 0s ease;} .spoilerbutton[value="Show Message"] + .spoiler > div {margin-top:-500%;} .spoilerbutton[value="Hide Message"] + .spoiler {padding:5px;}
  */
        if (grid[0][1] > 1 && grid[1][0] > 1) {
            return -1;
        }
        int m = grid.length, n = grid[0].length;
        int[][] dist = new int[m][n];
        for (var e : dist) {
            Arrays.fill(e, 1 << 30);
        }
        dist[0][0] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[] {0, 0, 0});
        int[] dirs = {-1, 0, 1, 0, -1};
        while (true) {
            var p = pq.poll();
            int i = p[1], j = p[2];
            if (i == m - 1 && j == n - 1) {
                return p[0];
            }
            for (int k = 0; k < 4; ++k) {
                int x = i + dirs[k], y = j + dirs[k + 1];
                if (x >= 0 && x < m && y >= 0 && y < n) {
                    int nt = p[0] + 1;
                    if (nt < grid[x][y]) {
                        nt = grid[x][y] + (grid[x][y] - nt) % 2;
                    }
                    if (nt < dist[x][y]) {
                        dist[x][y] = nt;
                        pq.offer(new int[] {nt, x, y});
                    }
                }
            }
        }
    }
}