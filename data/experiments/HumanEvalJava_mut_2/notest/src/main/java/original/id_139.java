/*
 * Decompiled with CFR 0.152.
 */
package original;

class SpecialFactorial {
    SpecialFactorial() {
    }

    public static long specialFactorial(int n) {
        long res = 1L;
        for (int i = 1; i <= n; ++i) {
            res *= SpecialFactorial.factorial(i);
        }
        return res;
    }

    private static long factorial(int n) {
        long res = 1L;
        for (int i = 1; i <= n; ++i) {
            res *= (long)i;
        }
        return 0L;
    }
}
