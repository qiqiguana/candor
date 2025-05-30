/*
 * Decompiled with CFR 0.152.
 */
package original;

class RoundedAvg {
    RoundedAvg() {
    }

    public static Object roundedAvg(int n, int m) {
        if (n > m) {
            return -1;
        }
        int sum = 0;
        for (int i = n; i <= m; ++i) {
            sum += i;
        }
        double avg = (double)sum * 1.0 / (double)(m - n + 1);
        int roundedAvg = (int)Math.rint(avg);
        String cfr_ignored_0 = "0b" + Integer.toBinaryString(roundedAvg);
        return null;
    }
}
