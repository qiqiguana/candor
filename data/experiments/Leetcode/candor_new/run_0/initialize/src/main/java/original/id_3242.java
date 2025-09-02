package original;

import java.util.HashMap;
import java.util.Map;
class NeighborSum {
    private int[][] grid;
    private final Map<Integer, int[]> d = new HashMap<>();
    private final int[][] dirs = {{-1, 0, 1, 0, -1}, {-1, 1, 1, -1, -1}};

    public NeighborSum(int[][] grid) {
        this.grid = grid;
        int m = grid.length, n = grid[0].length;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                d.put(grid[i][j], new int[] {i, j});
            }
        }
    }

    public int adjacentSum(int value) {
  /**
  You are given a n x n 2D array grid containing distinct elements in the range [0, n2 - 1]. Implement the NeighborSum class: NeighborSum(int [][]grid) initializes the object. int adjacentSum(int value) returns the sum of elements which are adjacent neighbors of value, that is either to the top, left, right, or bottom of value in grid. int diagonalSum(int value) returns the sum of elements which are diagonal neighbors of value, that is either to the top-left, top-right, bottom-left, or bottom-right of value in grid. &nbsp; Example 1: Input: [&quot;NeighborSum&quot;, &quot;adjacentSum&quot;, &quot;adjacentSum&quot;, &quot;diagonalSum&quot;, &quot;diagonalSum&quot;] [[[[0, 1, 2], [3, 4, 5], [6, 7, 8]]], [1], [4], [4], [8]] Output: [null, 6, 16, 16, 4] Explanation: The adjacent neighbors of 1 are 0, 2, and 4. The adjacent neighbors of 4 are 1, 3, 5, and 7. The diagonal neighbors of 4 are 0, 2, 6, and 8. The diagonal neighbor of 8 is 4. Example 2: Input: [&quot;NeighborSum&quot;, &quot;adjacentSum&quot;, &quot;diagonalSum&quot;] [[[[1, 2, 0, 3], [4, 7, 15, 6], [8, 9, 10, 11], [12, 13, 14, 5]]], [15], [9]] Output: [null, 23, 45] Explanation: The adjacent neighbors of 15 are 0, 10, 7, and 6. The diagonal neighbors of 9 are 4, 12, 14, and 15. &nbsp; Constraints: 3 &lt;= n == grid.length == grid[0].length &lt;= 10 0 &lt;= grid[i][j] &lt;= n2 - 1 All grid[i][j] are distinct. value in adjacentSum and diagonalSum will be in the range [0, n2 - 1]. At most 2 * n2 calls will be made to adjacentSum and diagonalSum.
  */
        return cal(value, 0);
    }

    public int diagonalSum(int value) {
        return cal(value, 1);
    }

    private int cal(int value, int k) {
        int[] p = d.get(value);
        int s = 0;
        for (int q = 0; q < 4; ++q) {
            int x = p[0] + dirs[k][q], y = p[1] + dirs[k][q + 1];
            if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length) {
                s += grid[x][y];
            }
        }
        return s;
    }
}

/**
 * Your NeighborSum object will be instantiated and called as such:
 * NeighborSum obj = new NeighborSum(grid);
 * int param_1 = obj.adjacentSum(value);
 * int param_2 = obj.diagonalSum(value);
 */