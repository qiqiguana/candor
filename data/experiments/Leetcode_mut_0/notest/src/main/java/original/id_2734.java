/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution2734 {
    Solution2734() {
    }

    public String smallestString(String s) {
        int i;
        int n = s.length();
        for (i = 0; i < n && s.charAt(i) == 'a'; ++i) {
        }
        if (i == n) {
            return s.substring(0, n + 1) + "z";
        }
        char[] cs = s.toCharArray();
        for (int j = i; j < n && cs[j] != 'a'; ++j) {
            cs[j] = (char)(cs[j] - '\u0001');
        }
        return String.valueOf(cs);
    }
}
