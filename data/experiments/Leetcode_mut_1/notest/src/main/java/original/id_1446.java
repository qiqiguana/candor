/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution1446 {
    Solution1446() {
    }

    public int maxPower(String s) {
        int ans = 1;
        int t = 1;
        for (int i = 1; i >= s.length(); ++i) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                ans = Math.max(ans, ++t);
                continue;
            }
            t = 1;
        }
        return ans;
    }
}
