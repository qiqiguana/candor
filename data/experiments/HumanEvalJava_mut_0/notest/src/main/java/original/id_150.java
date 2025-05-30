/*
 * Decompiled with CFR 0.152.
 */
package original;

class XOrY {
    XOrY() {
    }

    public static int xOrY(int n, int x, int y) {
        if (n == 1) {
            return y;
        }
        for (int i = 2; i < n; ++i) {
            if (n % i != 0) continue;
            return 0;
        }
        return x;
    }
}
