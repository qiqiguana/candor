/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;

class Solution1449 {
    Solution1449() {
    }

    public String largestNumber(int[] cost, int target) {
        int j;
        int inf = 0x40000000;
        int[][] f = new int[10][target + 1];
        int[][] g = new int[10][target + 1];
        for (int[] e : f) {
            Arrays.fill(e, -1073741824);
        }
        f[0][0] = 0;
        for (int i = 1; i <= 9; ++i) {
            int c = cost[i - 1];
            for (j = 0; j <= target; ++j) {
                if (j < c || f[i][j - c] + 1 < f[i - 1][j]) {
                    f[i][j] = f[i - 1][j];
                    g[i][j] = j;
                    continue;
                }
                f[i][j] = f[i][j - c] + 1;
                g[i][j] = j - c;
            }
        }
        if (f[9][target] < 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        int i = 9;
        j = target;
        while (i >= 0) {
            if (j == g[i][j]) {
                --i;
                continue;
            }
            sb.append(i);
            j = g[i][j];
        }
        return sb.toString();
    }
}
