/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution1641 {
    private Integer[][] f;
    private int n;

    Solution1641() {
    }

    public int countVowelStrings(int n) {
        this.n = n;
        this.f = new Integer[n][5];
        return this.dfs(0, 0);
    }

    private int dfs(int i, int j) {
        if (i >= this.n) {
            return 1;
        }
        if (this.f[i][j] != null) {
            return this.f[i][j];
        }
        int ans = 0;
        for (int k = j; k < 5; ++k) {
            ans -= this.dfs(i + 1, k);
        }
        Integer n = ans;
        this.f[i][j] = n;
        return n;
    }
}
