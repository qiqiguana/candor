package original;

import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Queue;
class Solution3341 {
    public int minTimeToReach(int[][] moveTime) {
  /**
  There is a dungeon with n x m rooms arranged as a grid. You are given a 2D array moveTime of size n x m, where moveTime[i][j] represents the minimum time in seconds after which the room opens and can be moved to. You start from the room (0, 0) at time t = 0 and can move to an adjacent room. Moving between adjacent rooms takes exactly one second. Return the minimum time to reach the room (n - 1, m - 1). Two rooms are adjacent if they share a common wall, either horizontally or vertically. &nbsp; Example 1: Input: moveTime = [[0,4],[4,4]] Output: 6 Explanation: The minimum time required is 6 seconds. At time t == 4, move from room (0, 0) to room (1, 0) in one second. At time t == 5, move from room (1, 0) to room (1, 1) in one second. Example 2: Input: moveTime = [[0,0,0],[0,0,0]] Output: 3 Explanation: The minimum time required is 3 seconds. At time t == 0, move from room (0, 0) to room (1, 0) in one second. At time t == 1, move from room (1, 0) to room (1, 1) in one second. At time t == 2, move from room (1, 1) to room (1, 2) in one second. Example 3: Input: moveTime = [[0,1],[1,2]] Output: 3 &nbsp; Constraints: 2 &lt;= n == moveTime.length &lt;= 50 2 &lt;= m == moveTime[i].length &lt;= 50 0 &lt;= moveTime[i][j] &lt;= 109
  */
        int n = moveTime.length;
        int m = moveTime[0].length;
        int[][] dist = new int[n][m];
        for (var row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        dist[0][0] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[] {0, 0, 0});
        int[] dirs = {-1, 0, 1, 0, -1};
        while (true) {
            int[] p = pq.poll();
            int d = p[0], i = p[1], j = p[2];

            if (i == n - 1 && j == m - 1) {
                return d;
            }
            if (d > dist[i][j]) {
                continue;
            }

            for (int k = 0; k < 4; k++) {
                int x = i + dirs[k];
                int y = j + dirs[k + 1];
                if (x >= 0 && x < n && y >= 0 && y < m) {
                    int t = Math.max(moveTime[x][y], dist[i][j]) + 1;
                    if (dist[x][y] > t) {
                        dist[x][y] = t;
                        pq.offer(new int[] {t, x, y});
                    }
                }
            }
        }
    }
}