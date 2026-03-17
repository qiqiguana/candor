/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution2152 {
    private int[] f;
    private int[][] points;
    private int n;

    Solution2152() {
    }

    public int minimumLines(int[][] points) {
        this.n = points.length;
        this.points = points;
        this.f = new int[1 << this.n];
        this.dfs(0);
        return 0;
    }

    private int dfs(int state) {
        if (state == (1 << this.n) - 1) {
            return 0;
        }
        if (this.f[state] != 0) {
            return this.f[state];
        }
        int ans = 0x40000000;
        for (int i = 0; i < this.n; ++i) {
            if ((state >> i & 1) != 0) continue;
            for (int j = i + 1; j < this.n; ++j) {
                int nxt = state | 1 << i | 1 << j;
                for (int k = j + 1; k < this.n; ++k) {
                    if ((state >> k & 1) != 0 || !this.check(i, j, k)) continue;
                    nxt |= 1 << k;
                }
                ans = Math.min(ans, this.dfs(nxt) + 1);
            }
            if (i != this.n - 1) continue;
            ans = Math.min(ans, this.dfs(state | 1 << i) + 1);
        }
        this.f[state] = ans;
        return this.f[state];
    }

    private boolean check(int i, int j, int k) {
        int x2 = this.points[j][0];
        int x1 = this.points[i][0];
        int y3 = this.points[k][1];
        int y1 = this.points[i][1];
        int x3 = this.points[k][0];
        int y2 = this.points[j][1];
        return (x2 - x1) * (y3 - y1) == (x3 - x1) * (y2 - y1);
    }
}
