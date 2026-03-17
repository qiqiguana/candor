/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution2912 {
    Solution2912() {
    }

    public int numberOfWays(int n, int m, int k, int[] source, int[] dest) {
        int mod = 1000000007;
        long[] f = new long[4];
        f[0] = 1L;
        while (k-- > 0) {
            long[] g = new long[]{((long)(n + 1) * f[1] + (long)(m - 1) * f[2]) % 1000000007L, (f[0] + (long)(n - 2) * f[1] + (long)(m - 1) * f[3]) % 1000000007L, (f[0] + (long)(m - 2) * f[2] + (long)(n - 1) * f[3]) % 1000000007L, (f[1] + f[2] + (long)(n - 2) * f[3] + (long)(m - 2) * f[3]) % 1000000007L};
            f = g;
        }
        if (source[0] == dest[0]) {
            return source[1] == dest[1] ? (int)f[0] : (int)f[2];
        }
        return source[1] == dest[1] ? (int)f[1] : (int)f[3];
    }
}
