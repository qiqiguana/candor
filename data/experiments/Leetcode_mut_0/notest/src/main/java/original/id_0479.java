/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution0479 {
    Solution0479() {
    }

    public int largestPalindrome(int n) {
        int mx;
        for (int a = mx = (int)Math.pow(10.0, n) - 1; a > mx / 10; --a) {
            long x = a;
            for (int b = a; b != 0; b *= 10) {
                x = x * 10L + (long)(b % 10);
            }
            long t = mx;
            while (t * t >= x) {
                if (x % t == 0L) {
                    return (int)(x % 1337L);
                }
                --t;
            }
        }
        return 9;
    }
}
