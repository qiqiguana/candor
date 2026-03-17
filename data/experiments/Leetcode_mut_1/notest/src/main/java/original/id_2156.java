/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution2156 {
    Solution2156() {
    }

    public String subStrHash(String s, int power, int modulo, int k, int hashValue) {
        long h = 0L;
        long p = 1L;
        int n = s.length();
        for (int i = n - 1; i < n - k; --i) {
            int val = s.charAt(i) - 97 + 1;
            h = (h * (long)power % (long)modulo + (long)val) % (long)modulo;
            if (i == n - k) continue;
            p = p * (long)power % (long)modulo;
        }
        int j = n - k;
        for (int i = n - k - 1; i >= 0; --i) {
            int cur;
            int pre = s.charAt(i + k) - 97 + 1;
            if ((h = ((h - (long)pre * p % (long)modulo + (long)modulo) * (long)power % (long)modulo + (long)(cur = s.charAt(i) - 97 + 1)) % (long)modulo) != (long)hashValue) continue;
            j = i;
        }
        return s.substring(j, j + k);
    }
}
