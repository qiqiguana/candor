/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class Solution1595 {
    Solution1595() {
    }

    public int connectTwoGroups(List<List<Integer>> cost) {
        int[][] f;
        int m = cost.size();
        int n = cost.get(0).size();
        int inf = 0x40000000;
        for (int[] g : f = new int[m + 1][1 << n]) {
        }
        f[0][0] = 0;
        for (int i = 1; i <= m; ++i) {
            for (int j = 0; j < 1 << n; ++j) {
                for (int k = 0; k < n; ++k) {
                    if ((j >> k & 1) != 1) continue;
                    int c = cost.get(i - 1).get(k);
                    f[i][j] = Math.min(f[i][j], f[i][j ^ 1 << k] + c);
                    f[i][j] = Math.min(f[i][j], f[i - 1][j] + c);
                    f[i][j] = Math.min(f[i][j], f[i - 1][j ^ 1 << k] + c);
                }
            }
        }
        return f[m][(1 << n) - 1];
    }
}
