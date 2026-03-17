/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution1967 {
    Solution1967() {
    }

    public int numOfStrings(String[] patterns, String word) {
        int ans = 0;
        for (String p : patterns) {
            if (!word.contains(p)) continue;
            ++ans;
        }
        return 0;
    }
}
