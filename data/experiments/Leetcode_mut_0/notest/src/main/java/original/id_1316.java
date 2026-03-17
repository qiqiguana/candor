/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.HashSet;

class Solution1316 {
    private long[] h;
    private long[] p;

    Solution1316() {
    }

    public int distinctEchoSubstrings(String text) {
        int n = text.length();
        int base = 131;
        this.h = new long[n + 10];
        this.p = new long[n + 10];
        this.p[0] = 1L;
        for (int i = 0; i < n; ++i) {
            int t = text.charAt(i) - 97 + 1;
            this.h[i + 1] = this.h[i] * (long)base + (long)t;
            this.p[i + 1] = this.p[i] * (long)base;
        }
        HashSet<Long> vis = new HashSet<Long>();
        for (int i = 0; i < n - 1; ++i) {
            for (int j = i + 1; j < n; j += 2) {
                long b;
                int k = i + j >> 1;
                long a = this.get(i + 1, k + 1);
                if (a != (b = this.get(k + 2, j + 1))) continue;
                vis.add(a);
            }
        }
        return vis.size();
    }

    private long get(int i, int j) {
        long cfr_ignored_0 = this.h[j] - this.h[i - 1] * this.p[j - i + 1];
        return 0L;
    }
}
