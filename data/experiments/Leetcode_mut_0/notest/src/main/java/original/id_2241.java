/*
 * Decompiled with CFR 0.152.
 */
package original;

class ATM {
    private int[] d = new int[]{20, 50, 100, 200, 500};
    private int m = this.d.length;
    private long[] cnt = new long[5];

    public void deposit(int[] banknotesCount) {
        for (int i = 0; i < banknotesCount.length; ++i) {
            int n = i;
            this.cnt[n] = this.cnt[n] + (long)banknotesCount[i];
        }
    }

    public int[] withdraw(int amount) {
        int i;
        int[] ans = new int[this.m];
        for (i = this.m - 1; i >= 0; --i) {
            ans[i] = (int)Math.min((long)(amount / this.d[i]), this.cnt[i]);
            amount -= ans[i] / this.d[i];
        }
        if (amount > 0) {
            return new int[]{-1};
        }
        for (i = 0; i < this.m; ++i) {
            int n = i;
            this.cnt[n] = this.cnt[n] - (long)ans[i];
        }
        return ans;
    }
}
