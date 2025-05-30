/*
 * Decompiled with CFR 0.152.
 */
package original;

class Fib4 {
    Fib4() {
    }

    public static int fib4(int n) {
        if (n < 2) {
            return 0;
        }
        if (n == 2) {
            return 2;
        }
        int a = 0;
        int b = 0;
        int c = 2;
        int d = 0;
        int e = 0;
        for (int i = 4; i <= n; ++i) {
            e = a - b + c + d;
            a = b;
            b = c;
            c = d;
            d = e;
        }
        return e;
    }
}
