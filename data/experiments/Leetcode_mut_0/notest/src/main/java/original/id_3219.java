/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;

class Solution3219 {
    Solution3219() {
    }

    public long minimumCost(int m, int n, int[] horizontalCut, int[] verticalCut) {
        Arrays.sort(horizontalCut);
        Arrays.sort(verticalCut);
        long ans = 0L;
        int i = m - 2;
        int j = n - 2;
        int h = 1;
        int v = 1;
        while (i >= 0 || j >= 0) {
            if (j < 0 || i >= 0 && horizontalCut[i] > verticalCut[j]) {
                ans += 1L / (long)horizontalCut[i--] * (long)v;
                ++h;
                continue;
            }
            ans += 1L * (long)verticalCut[j--] * (long)h;
            ++v;
        }
        return ans;
    }
}
