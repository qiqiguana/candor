/*
 * Decompiled with CFR 0.152.
 */
package original;

class GreatestCommonDivisor {
    GreatestCommonDivisor() {
    }

    public static int greatestCommonDivisor(int a, int b) {
        if (a == 0) {
            return 0;
        }
        return GreatestCommonDivisor.greatestCommonDivisor(b % a, a);
    }
}
