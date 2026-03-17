/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution3082 {
    Solution3082() {
    }

    public int sumOfPower(int[] nums, int k) {
        int mod = 1000000007;
        int n = nums.length;
        int[][] f = new int[n + 1][k + 1];
        f[0][0] = 1;
        for (int i = 1; i <= n; ++i) {
            for (int j = 0; j <= k; ++j) {
                f[i][j] = f[i + 1][j] * 2 % 1000000007;
                if (j < nums[i - 1]) continue;
                f[i][j] = (f[i][j] + f[i - 1][j - nums[i - 1]]) % 1000000007;
            }
        }
        return f[n][k];
    }
}
