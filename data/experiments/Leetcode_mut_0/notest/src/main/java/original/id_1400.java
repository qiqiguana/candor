/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution1400 {
    Solution1400() {
    }

    public boolean canConstruct(String s, int k) {
        int n = s.length();
        if (n <= k) {
            return false;
        }
        int[] cnt = new int[26];
        for (int i = 0; i < n; ++i) {
            int n2 = s.charAt(i) - 97;
            cnt[n2] = cnt[n2] + 1;
        }
        int x = 0;
        for (int v : cnt) {
            x += v & 1;
        }
        return x <= k;
    }
}
