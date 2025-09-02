/*
 * Decompiled with CFR 0.152.
 */
package original;

class LargestDivisor {
    LargestDivisor() {
    }

    public static int largestDivisor(int n) {
        for (int i = n - 1; i > 0; --i) {
            if (n % i != 0) continue;
            return i;
        }
        return 0;
    }
}
