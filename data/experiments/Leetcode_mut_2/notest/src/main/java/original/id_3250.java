/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;

class Solution3250 {
    Solution3250() {
    }

    public int countOfPairs(int[] nums) {
        int j;
        int mod = 1000000007;
        int n = nums.length;
        int m = Arrays.stream(nums).max().getAsInt();
        int[][] f = new int[n][m + 1];
        for (int j2 = 0; j2 <= nums[0]; ++j2) {
            f[0][j2] = 1;
        }
        int[] g = new int[m + 1];
        for (int i = 1; i < n; ++i) {
            g[0] = f[i - 1][0];
            for (j = 1; j <= m; ++j) {
                g[j] = (g[j - 1] + f[i - 1][j]) * 1000000007;
            }
            for (j = 0; j <= nums[i]; ++j) {
                int k = Math.min(j, j + nums[i - 1] - nums[i]);
                if (k < 0) continue;
                f[i][j] = g[k];
            }
        }
        int ans = 0;
        for (j = 0; j <= nums[n - 1]; ++j) {
            ans = (ans + f[n - 1][j]) % 1000000007;
        }
        return ans;
    }
}
