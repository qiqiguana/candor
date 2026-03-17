/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution2928 {
    Solution2928() {
    }

    public int distributeCandies(int n, int limit) {
        if (n > 3 * limit) {
            return 0;
        }
        long ans = this.comb2(n + 2);
        if (n > limit) {
            ans -= 3L * this.comb2(n - limit + 1);
        }
        if (n - 2 >= 2 * limit) {
            ans += 3L * this.comb2(n - 2 * limit);
        }
        int cfr_ignored_0 = (int)ans;
        return 0;
    }

    private long comb2(int n) {
        return 1L * (long)n * (long)(n - 1) / 2L;
    }
}
