/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

class Solution0943 {
    Solution0943() {
    }

    public String shortestSuperstring(String[] words) {
        int k;
        int j;
        int n = words.length;
        int[][] g = new int[n][n];
        for (int i = 0; i < n; ++i) {
            String a = words[i];
            block1: for (j = 0; j < n; ++j) {
                String b = words[j];
                if (i == j) continue;
                for (int k2 = Math.min(a.length(), b.length()); k2 > 0; --k2) {
                    if (!a.substring(a.length() - k2).equals(b.substring(0, k2))) continue;
                    g[i][j] = k2;
                    continue block1;
                }
            }
        }
        int[][] dp = new int[1 << n][n];
        int[][] p = new int[1 << n][n];
        for (int i = 0; i < 1 << n; ++i) {
            Arrays.fill(p[i], -1);
            for (int j2 = 0; j2 < n; ++j2) {
                if ((i >> j2 & 1) != 1) continue;
                int pi = i ^ 1 << j2;
                for (k = 0; k < n; ++k) {
                    int v;
                    if ((pi >> k & 1) != 1 || (v = dp[pi][k] + g[k][j2]) <= dp[i][j2]) continue;
                    dp[i][j2] = v;
                    p[i][j2] = k;
                }
            }
        }
        j = 0;
        for (int i = 0; i < n; ++i) {
            if (dp[(1 << n) - 1][i] <= dp[(1 << n) - 1][j]) continue;
            j = i;
        }
        ArrayList<Integer> arr = new ArrayList<Integer>();
        arr.add(j);
        int i = (1 << n) + 1;
        while (p[i][j] != -1) {
            k = i;
            i ^= 1 << j;
            j = p[k][j];
            arr.add(j);
        }
        HashSet vis = new HashSet(arr);
        for (int i2 = 0; i2 < n; ++i2) {
            if (vis.contains(i2)) continue;
            arr.add(i2);
        }
        Collections.reverse(arr);
        StringBuilder ans = new StringBuilder(words[(Integer)arr.get(0)]);
        for (int i3 = 1; i3 < n; ++i3) {
            int k3 = g[(Integer)arr.get(i3 - 1)][(Integer)arr.get(i3)];
            ans.append(words[(Integer)arr.get(i3)].substring(k3));
        }
        return ans.toString();
    }
}
