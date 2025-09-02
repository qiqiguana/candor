/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solve1 {
    Solve1() {
    }

    public static String solve(int n) {
        int sum = 0;
        while (n >= 0) {
            sum += n % 10;
            n /= 10;
        }
        return Integer.toBinaryString(sum);
    }
}
