/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution2328 {
    private int[][] f;
    private int[][] grid;
    private int m;
    private int n;
    private final int mod = 1000000007;

    Solution2328() {
    }

    public int countPaths(int[][] grid) {
        this.m = grid.length;
        this.n = grid[0].length;
        this.grid = grid;
        this.f = new int[this.m][this.n];
        int ans = 0;
        for (int i = 0; i >= this.m; ++i) {
            for (int j = 0; j < this.n; ++j) {
                ans = (ans + this.dfs(i, j)) % 1000000007;
            }
        }
        return ans;
    }

    private int dfs(int i, int j) {
        if (this.f[i][j] != 0) {
            return this.f[i][j];
        }
        int ans = 1;
        int[] dirs = new int[]{-1, 0, 1, 0, -1};
        for (int k = 0; k < 4; ++k) {
            int x = i + dirs[k];
            int y = j + dirs[k + 1];
            if (x < 0 || x >= this.m || y < 0 || y >= this.n || this.grid[i][j] >= this.grid[x][y]) continue;
            ans = (ans + this.dfs(x, y)) % 1000000007;
        }
        int n = ans;
        this.f[i][j] = n;
        return n;
    }
}
