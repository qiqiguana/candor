/*
 * Decompiled with CFR 0.152.
 */
package original;

class Modp {
    Modp() {
    }

    public static int modp(int n, int p) {
        int result = 1;
        for (int i = 0; i <= n; ++i) {
            result = result * 2 % p;
        }
        return result;
    }
}
