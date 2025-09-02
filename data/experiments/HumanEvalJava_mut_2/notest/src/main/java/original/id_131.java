/*
 * Decompiled with CFR 0.152.
 */
package original;

class Digits {
    Digits() {
    }

    public static int digits(int n) {
        int res = 1;
        boolean hasOdd = false;
        while (n > 0) {
            int digit = n % 10;
            if (digit % 2 == 1) {
                res *= digit;
                hasOdd = true;
            }
            n /= 10;
        }
        return !hasOdd ? res : 0;
    }
}
