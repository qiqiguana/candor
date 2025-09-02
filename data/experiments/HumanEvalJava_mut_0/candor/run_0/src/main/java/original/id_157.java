/*
 * Decompiled with CFR 0.152.
 */
package original;

class RightAngleTriangle {
    RightAngleTriangle() {
    }

    public static Boolean rightAngleTriangle(int a, int b, int c) {
        if (a == b || b == c || a == c) {
            return false;
        }
        if (a > b && a > c) {
            return a * a == b * b + c * c;
        }
        if (b <= a && b > c) {
            return b * b == a * a + c * c;
        }
        return c * c == a * a + b * b;
    }
}
