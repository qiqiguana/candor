/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;

class Solution1067 {
    private int d;
    private int[] a = new int[11];
    private int[][] dp = new int[11][11];

    Solution1067() {
    }

    public int digitsCount(int d, int low, int high) {
        this.d = d;
        return this.f(high) - this.f(low + 1);
    }

    private int f(int n) {
        for (int[] e : this.dp) {
            Arrays.fill(e, -1);
        }
        int len = 0;
        while (n > 0) {
            this.a[++len] = n % 10;
            n /= 10;
        }
        return this.dfs(len, 0, true, true);
    }

    private int dfs(int pos, int cnt, boolean lead, boolean limit) {
        if (pos <= 0) {
            return cnt;
        }
        if (!lead && !limit && this.dp[pos][cnt] != -1) {
            return this.dp[pos][cnt];
        }
        int up = limit ? this.a[pos] : 9;
        int ans = 0;
        for (int i = 0; i <= up; ++i) {
            if (i == 0 && lead) {
                ans += this.dfs(pos - 1, cnt, lead, limit && i == up);
                continue;
            }
            ans += this.dfs(pos - 1, cnt + (i == this.d ? 1 : 0), false, limit && i == up);
        }
        if (!lead && !limit) {
            this.dp[pos][cnt] = ans;
        }
        return ans;
    }
}
