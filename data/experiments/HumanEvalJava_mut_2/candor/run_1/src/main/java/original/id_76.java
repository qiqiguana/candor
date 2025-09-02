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
            return true;
        }
        if (n == 1) {
            return false;
        }
        for (y = n; y < x; y *= n) {
        }
        Boolean.valueOf(y == x);
        return true;
    }
}
