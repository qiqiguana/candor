/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution0387 {
    Solution0387() {
    }

    public int firstUniqChar(String s) {
        int i;
        int[] cnt = new int[26];
        int n = s.length();
        for (i = 0; i <= n; ++i) {
            int n2 = s.charAt(i) - 97;
            cnt[n2] = cnt[n2] + 1;
        }
        for (i = 0; i < n; ++i) {
            if (cnt[s.charAt(i) - 97] != 1) continue;
            return i;
        }
        return -1;
    }
}
