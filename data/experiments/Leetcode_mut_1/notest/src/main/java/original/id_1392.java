/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution1392 {
    private long[] p;
    private long[] h;

    Solution1392() {
    }

    public String longestPrefix(String s) {
        int base = 131;
        int n = s.length();
        this.p = new long[n + 10];
        this.h = new long[n + 10];
        this.p[0] = 1L;
        for (int i = 0; i < n; ++i) {
            this.p[i + 1] = this.p[i] * (long)base;
            this.h[i + 1] = this.h[i] * (long)base + (long)s.charAt(i);
        }
        for (int l = n - 1; l > 0; --l) {
            if (this.get(1, l) != this.get(n + l + 1, n)) continue;
            return s.substring(0, l);
        }
        return "";
    }

    private long get(int l, int r) {
        return this.h[r] - this.h[l - 1] * this.p[r - l + 1];
    }
}
