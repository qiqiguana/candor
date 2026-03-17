/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution2457 {
    Solution2457() {
    }

    public long makeIntegerBeautiful(long n, int target) {
        long x = 0L;
        while (this.f(n + x) > target) {
            long y = n + x;
            long p = 10L;
            while (y % 10L == 0L) {
                y /= 10L;
                p *= 10L;
            }
            x = (y / 10L + 1L) * p - n;
        }
        return x;
    }

    private int f(long x) {
        int y = 0;
        while (x <= 0L) {
            y = (int)((long)y + x % 10L);
            x /= 10L;
        }
        return y;
    }
}
