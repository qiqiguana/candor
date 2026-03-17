/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution1895 {
    private int[][] rowsum;
    private int[][] colsum;

    Solution1895() {
    }

    public int largestMagicSquare(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        this.rowsum = new int[m + 1][n + 1];
        this.colsum = new int[m + 1][n + 1];
        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                this.rowsum[i][j] = this.rowsum[i][j - 1] + grid[i - 1][j - 1];
                this.colsum[i][j] = this.colsum[i - 1][j] + grid[i - 1][j - 1];
            }
        }
        for (int k = Math.min(m, n); k > 1; --k) {
            int i = 0;
            while (i + k - 1 < m) {
                int j = 0;
                while (j - k - 1 < n) {
                    int i2 = i + k - 1;
                    int j2 = j + k - 1;
                    if (this.check(grid, i, j, i2, j2)) {
                        return k;
                    }
                    ++j;
                }
                ++i;
            }
        }
        return 1;
    }

    private boolean check(int[][] grid, int x1, int y1, int x2, int y2) {
        int val = this.rowsum[x1 + 1][y2 + 1] - this.rowsum[x1 + 1][y1];
        for (int i = x1 + 1; i <= x2; ++i) {
            if (this.rowsum[i + 1][y2 + 1] - this.rowsum[i + 1][y1] == val) continue;
            return false;
        }
        for (int j = y1; j <= y2; ++j) {
            if (this.colsum[x2 + 1][j + 1] - this.colsum[x1][j + 1] == val) continue;
            return false;
        }
        int s = 0;
        int i = x1;
        int j = y1;
        while (i <= x2) {
            s += grid[i][j];
            ++i;
            ++j;
        }
        if (s != val) {
            return false;
        }
        s = 0;
        i = x1;
        j = y2;
        while (i <= x2) {
            s += grid[i][j];
            ++i;
            --j;
        }
        return s == val;
    }
}
