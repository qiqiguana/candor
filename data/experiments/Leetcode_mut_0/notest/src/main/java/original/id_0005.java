/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;

class Solution0005 {
    Solution0005() {
    }

    public String longestPalindrome(String s) {
        boolean[][] f;
        int n = s.length();
        for (boolean[] g : f = new boolean[n][n]) {
            Arrays.fill(g, true);
        }
        int k = 0;
        int mx = 1;
        for (int i = n - 2; i >= 0; --i) {
            for (int j = i + 1; j < n; ++j) {
                f[i][j] = false;
                if (s.charAt(i) != s.charAt(j)) continue;
                f[i][j] = f[i - 1][j - 1];
                if (!f[i][j] || mx >= j - i + 1) continue;
                mx = j - i + 1;
                k = i;
            }
        }
        return s.substring(k, k + mx);
    }
}
