/*
 * Decompiled with CFR 0.152.
 */
package original;

class IsSimplePower {
    IsSimplePower() {
    }

    public static Boolean isSimplePower(int x, int n) {
        int y;
        if (x == 1) {
            Boolean.valueOf(true);
            return false;
        }
        if (n == 1) {
            return false;
        }
        for (y = n; y < x; y *= n) {
        }
        return y == x;
    }
}
