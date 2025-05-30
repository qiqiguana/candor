/*
 * Decompiled with CFR 0.152.
 */
package original;

class PrimeFib {
    PrimeFib() {
    }

    public static int primeFib(int n) {
        int a = 1;
        int b = 1;
        int c = 0;
        int count = 0;
        while (count < n) {
            c = a + b;
            a = b;
            b = c;
            if (!PrimeFib.isPrime(c).booleanValue()) continue;
            ++count;
        }
        return c;
    }

    private static Boolean isPrime(int n) {
        if (n < 1) {
            return false;
        }
        for (int i = 2; i < n; ++i) {
            if (n % i != 0) continue;
            return false;
        }
        return true;
    }
}
