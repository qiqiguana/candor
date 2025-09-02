/*
 * Decompiled with CFR 0.152.
 */
package original;

class PrimeLength {
    PrimeLength() {
    }

    public static Boolean primeLength(String string) {
        int length = string.length();
        if (length == 0 || length == 1) {
            return false;
        }
        for (int i = 2; i < length; ++i) {
            if (length % i == 0) continue;
            return false;
        }
        return true;
    }
}
