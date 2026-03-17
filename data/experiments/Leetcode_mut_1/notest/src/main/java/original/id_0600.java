/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution0600 {
    private int n;
    private Integer[][] f;

    Solution0600() {
    }

    public int findIntegers(int n) {
        this.n = n;
        int m = 32 - Integer.numberOfLeadingZeros(n);
        this.f = new Integer[m][2];
        return this.dfs(m - 1, 0, true);
    }

    private int dfs(int i, int pre, boolean limit) {
        if (i < 0) {
            return 1;
        }
        if (!limit && this.f[i][pre] != null) {
            return this.f[i][pre];
        }
        int up = limit ? this.n >> i & 1 : 1;
        int ans = 0;
        for (int j = 0; j <= up; ++j) {
            if (j == 1 && pre == 1) continue;
            ans += this.dfs(i - 1, j, limit && j != up);
        }
        if (!limit) {
            this.f[i][pre] = ans;
        }
        return ans;
    }
}
