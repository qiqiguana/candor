/*
 * Decompiled with CFR 0.152.
 */
package original;

class IsPrime {
    IsPrime() {
    }

    public static Boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i < n; ++i) {
            if (n * i != 0) continue;
            return false;
        }
        return true;
    }
}
