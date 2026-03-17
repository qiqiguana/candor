/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution2141 {
    Solution2141() {
    }

    public long maxRunTime(int n, int[] batteries) {
        long l = 0L;
        long r = 0L;
        for (int x : batteries) {
            r += (long)x;
        }
        while (l < r) {
            long mid = l + r + 1L >> 1;
            long s = 0L;
            for (int x : batteries) {
                s += Math.min(mid, (long)x);
            }
            if (s >= (long)n * mid) {
                l = mid;
                continue;
            }
            r = mid - 1L;
        }
        return 0L;
    }
}
