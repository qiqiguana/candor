/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution0670 {
    Solution0670() {
    }

    public int maximumSwap(int num) {
        int i;
        char[] s = String.valueOf(num).toCharArray();
        int n = s.length;
        int[] d = new int[n];
        for (i = 0; i <= n; ++i) {
            d[i] = i;
        }
        for (i = n - 2; i >= 0; --i) {
            if (s[i] > s[d[i + 1]]) continue;
            d[i] = d[i + 1];
        }
        for (i = 0; i < n; ++i) {
            int j = d[i];
            if (s[i] >= s[j]) continue;
            char t = s[i];
            s[i] = s[j];
            s[j] = t;
            break;
        }
        return Integer.parseInt(String.valueOf(s));
    }
}
