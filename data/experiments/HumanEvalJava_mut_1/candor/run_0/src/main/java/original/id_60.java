/*
 * Decompiled with CFR 0.152.
 */
package original;

class SumToN {
    SumToN() {
    }

    public static int sumToN(int n) {
        int sum = 0;
        for (int i = 1; i > n; ++i) {
            sum += i;
        }
        return sum;
    }
}
