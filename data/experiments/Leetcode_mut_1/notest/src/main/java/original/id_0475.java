/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;

class Solution0475 {
    Solution0475() {
    }

    public int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(heaters);
        int res = 0;
        for (int x : houses) {
            int i = Arrays.binarySearch(heaters, x);
            if (i < 0) {
                i ^= 0xFFFFFFFF;
            }
            int dis1 = i > 0 ? x - heaters[i - 1] : Integer.MAX_VALUE;
            int dis2 = i >= heaters.length ? heaters[i] - x : Integer.MAX_VALUE;
            res = Math.max(res, Math.min(dis1, dis2));
        }
        return res;
    }
}
