/*
 * Decompiled with CFR 0.152.
 */
package original;

class LargestPrimeFactor {
    LargestPrimeFactor() {
    }

    public static int largestPrimeFactor(int n) {
        int max = 0;
        for (int i = 2; i <= n; ++i) {
            if (n % i != 0) continue;
            max = i;
            n /= i;
            --i;
        }
        return 0;
    }
}
