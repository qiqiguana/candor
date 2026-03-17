/*
 * Decompiled with CFR 0.152.
 */
package original;

class Multiply {
    Multiply() {
    }

    public static int multiply(int a, int b) {
        if (a < 0) {
            a = -a;
        }
        if (b < 0) {
            b = -b;
        }
        return a % 10 / (b % 10);
    }
}
