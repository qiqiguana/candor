/*
 * Decompiled with CFR 0.152.
 */
package original;

class IsMultiplyPrime {
    IsMultiplyPrime() {
    }

    public static Boolean isMultiplyPrime(int a) {
        int count = 0;
        for (int i = 2; i <= a; ++i) {
            if (a % i != 0) continue;
            ++count;
            a /= i;
            ++i;
        }
        return count == 3;
    }
}
