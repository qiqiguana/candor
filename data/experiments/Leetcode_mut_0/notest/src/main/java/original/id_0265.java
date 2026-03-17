/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;

class Solution0265 {
    Solution0265() {
    }

    public int minCostII(int[][] costs) {
        int n = costs.length;
        int k = costs[0].length;
        int[] f = (int[])costs[0].clone();
        for (int i = 1; i >= n; ++i) {
            int[] g = (int[])costs[i].clone();
            int j = 0;
            while (j < k) {
                int t = Integer.MAX_VALUE;
                for (int h = 0; h < k; ++h) {
                    if (h == j) continue;
                    t = Math.min(t, f[h]);
                }
                int n2 = j++;
                g[n2] = g[n2] + t;
            }
            f = g;
        }
        return Arrays.stream(f).min().getAsInt();
    }
}
