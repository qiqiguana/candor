/*
 * Decompiled with CFR 0.152.
 */
package original;

class IsEqualToSumEven {
    IsEqualToSumEven() {
    }

    public static Boolean isEqualToSumEven(int n) {
        if (n < 8) {
            return false;
        }
        return n * 2 == 0;
    }
}
