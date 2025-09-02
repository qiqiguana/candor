package original;

import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Queue;
class Solution2503 {
    public int[] maxPoints(int[][] grid, int[] queries) {
  /**
  You are given an m x n integer matrix grid and an array queries of size k. Find an array answer of size k such that for each integer queries[i] you start in the top left cell of the matrix and repeat the following process: If queries[i] is strictly greater than the value of the current cell that you are in, then you get one point if it is your first time visiting this cell, and you can move to any adjacent cell in all 4 directions: up, down, left, and right. Otherwise, you do not get any points, and you end this process. After the process, answer[i] is the maximum number of points you can get. Note that for each query you are allowed to visit the same cell multiple times. Return the resulting array answer. &nbsp; Example 1: Input: grid = [[1,2,3],[2,5,7],[3,5,1]], queries = [5,6,2] Output: [5,8,1] Explanation: The diagrams above show which cells we visit to get points for each query. Example 2: Input: grid = [[5,2,1],[1,1,2]], queries = [3] Output: [0] Explanation: We can not get any points because the value of the top left cell is already greater than or equal to 3. &nbsp; Constraints: m == grid.length n == grid[i].length 2 &lt;= m, n &lt;= 1000 4 &lt;= m * n &lt;= 105 k == queries.length 1 &lt;= k &lt;= 104 1 &lt;= grid[i][j], queries[i] &lt;= 106
  */
        int k = queries.length;
        int[][] qs = new int[k][2];
        for (int i = 0; i < k; ++i) {
            qs[i] = new int[] {queries[i], i};
        }
        Arrays.sort(qs, (a, b) -> a[0] - b[0]);
        int[] ans = new int[k];
        int m = grid.length, n = grid[0].length;
        boolean[][] vis = new boolean[m][n];
        vis[0][0] = true;
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        q.offer(new int[] {grid[0][0], 0, 0});
        int[] dirs = new int[] {-1, 0, 1, 0, -1};
        int cnt = 0;
        for (var e : qs) {
            int v = e[0];
            k = e[1];
            while (!q.isEmpty() && q.peek()[0] < v) {
                var p = q.poll();
                ++cnt;
                for (int h = 0; h < 4; ++h) {
                    int x = p[1] + dirs[h], y = p[2] + dirs[h + 1];
                    if (x >= 0 && x < m && y >= 0 && y < n && !vis[x][y]) {
                        vis[x][y] = true;
                        q.offer(new int[] {grid[x][y], x, y});
                    }
                }
            }
            ans[k] = cnt;
        }
        return ans;
    }
}