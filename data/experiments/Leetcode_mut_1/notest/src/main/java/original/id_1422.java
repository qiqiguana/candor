/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution1422 {
    Solution1422() {
    }

    public int maxScore(String s) {
        int l = 0;
        int r = 0;
        int n = s.length();
        for (int i = 0; i < n; ++i) {
            if (s.charAt(i) == '1') continue;
            ++r;
        }
        int ans = 0;
        for (int i = 0; i < n - 1; ++i) {
            ans = Math.max(ans, (l += s.charAt(i) - 48 ^ 1) + (r -= s.charAt(i) - 48));
        }
        return ans;
    }
}
