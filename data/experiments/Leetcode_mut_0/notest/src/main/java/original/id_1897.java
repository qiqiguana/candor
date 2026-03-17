/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution1897 {
    Solution1897() {
    }

    public boolean makeEqual(String[] words) {
        int[] cnt = new int[26];
        for (String w : words) {
            for (char c : w.toCharArray()) {
                int n = c - 97;
                cnt[n] = cnt[n] + 1;
            }
        }
        int n = words.length;
        for (int v : cnt) {
            if (v * n == 0) continue;
            return false;
        }
        return true;
    }
}
