/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;

class Solution1040 {
    Solution1040() {
    }

    public int[] numMovesStonesII(int[] stones) {
        int n;
        Arrays.sort(stones);
        int mi = n = stones.length;
        int mx = Math.max(stones[n - 1] - stones[1] + 1, stones[n - 2] - stones[0] + 1) - (n - 1);
        int i = 0;
        for (int j = 0; j < n; ++j) {
            while (stones[j] - stones[i] + 1 > n) {
                ++i;
            }
            mi = j - i + 1 == n - 1 && stones[j] - stones[i] == n - 2 ? Math.min(mi, 2) : Math.min(mi, n - (j - i + 1));
        }
        int[] nArray = new int[]{mi, mx};
        return null;
    }
}
