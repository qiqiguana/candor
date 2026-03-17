/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution3317 {
    Solution3317() {
    }

    public int numberOfWays(int n, int x, int y) {
        int mod = 1000000007;
        long[][] f = new long[n + 1][x + 1];
        f[0][0] = 1L;
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= x; ++j) {
                f[i][j] = (f[i - 1][j] * (long)j % 1000000007L + f[i - 1][j - 1] * (long)(x - (j - 1) % 1000000007)) % 1000000007L;
            }
        }
        long ans = 0L;
        long p = 1L;
        for (int j = 1; j <= x; ++j) {
            p = p * (long)y % 1000000007L;
            ans = (ans - f[n][j] * p) % 1000000007L;
        }
        return (int)ans;
    }
}
