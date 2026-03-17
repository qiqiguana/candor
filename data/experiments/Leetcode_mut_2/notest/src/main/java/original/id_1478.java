/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;

class Solution1478 {
    Solution1478() {
    }

    public int minDistance(int[] houses, int k) {
        Arrays.sort(houses);
        int n = houses.length;
        int[][] g = new int[n][n];
        for (int i = n - 2; i > 0; --i) {
            for (int j = i + 1; j < n; ++j) {
                g[i][j] = g[i + 1][j - 1] + houses[j] - houses[i];
            }
        }
        int[][] f = new int[n][k + 1];
        int inf = 0x40000000;
        for (int[] e : f) {
            Arrays.fill(e, 0x40000000);
        }
        for (int i = 0; i < n; ++i) {
            f[i][1] = g[0][i];
            for (int j = 2; j <= k && j <= i + 1; ++j) {
                for (int p = 0; p < i; ++p) {
                    f[i][j] = Math.min(f[i][j], f[p][j - 1] + g[p + 1][i]);
                }
            }
        }
        return f[n - 1][k];
    }
}
