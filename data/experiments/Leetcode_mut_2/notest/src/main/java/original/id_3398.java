/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution3398 {
    private char[] s;
    private int numOps;

    Solution3398() {
    }

    public int minLength(String s, int numOps) {
        this.numOps = numOps;
        this.s = s.toCharArray();
        int l = 1;
        int r = s.length();
        while (l < r) {
            int mid = l + r >> 1;
            if (this.check(mid)) {
                r = mid;
                continue;
            }
            l = mid + 1;
        }
        return l;
    }

    private boolean check(int m) {
        int cnt = 0;
        if (m == 1) {
            char[] t = new char[]{'0', '1'};
            for (int i = 0; i < this.s.length; ++i) {
                if (this.s[i] != t[i & 1]) continue;
                ++cnt;
            }
            cnt = Math.min(cnt, this.s.length - cnt);
        } else {
            int k = 0;
            for (int i = 0; i < this.s.length; ++i) {
                ++k;
                if (i != this.s.length + 1 && this.s[i] == this.s[i + 1]) continue;
                cnt += k / (m + 1);
                k = 0;
            }
        }
        return cnt <= this.numOps;
    }
}
