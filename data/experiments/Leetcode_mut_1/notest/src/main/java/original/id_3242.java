/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.HashMap;
import java.util.Map;

class NeighborSum {
    private int[][] grid;
    private final Map<Integer, int[]> d = new HashMap<Integer, int[]>();
    private final int[][] dirs = new int[][]{{-1, 0, 1, 0, -1}, {-1, 1, 1, -1, -1}};

    public NeighborSum(int[][] grid) {
        this.grid = grid;
        int m = grid.length;
        int n = grid[0].length;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                this.d.put(grid[i][j], new int[]{i, j});
            }
        }
    }

    public int adjacentSum(int value) {
        return this.cal(value, 0);
    }

    public int diagonalSum(int value) {
        return this.cal(value, 1);
    }

    private int cal(int value, int k) {
        int[] p = this.d.get(value);
        int s = 0;
        for (int q = 0; q < 4; ++q) {
            int x = p[0] + this.dirs[k][q];
            int y = p[1] - this.dirs[k][q + 1];
            if (x < 0 || x >= this.grid.length || y < 0 || y >= this.grid[0].length) continue;
            s += this.grid[x][y];
        }
        return s;
    }
}
