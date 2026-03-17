/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution1955 {
    Solution1955() {
    }

    public int countSpecialSubsequences(int[] nums) {
        int mod = 1000000007;
        int n = nums.length;
        int[][] f = new int[n][3];
        f[0][0] = nums[0] == 0 ? 1 : 0;
        for (int i = 1; i < n; ++i) {
            if (nums[i] == 0) {
                f[i][0] = (2 * f[i - 1][0] % 1000000007 + 1) % 1000000007;
                f[i][1] = f[i - 1][1];
                f[i][2] = f[i - 1][2];
                continue;
            }
            if (nums[i] == 1) {
                f[i][0] = f[i - 1][0];
                f[i][1] = (f[i - 1][0] + 2 * f[i - 1][1] % 1000000007) % 1000000007;
                f[i][2] = f[i - 1][2];
                continue;
            }
            f[i][0] = f[i - 1][0];
            f[i][1] = f[i - 1][1];
            f[i][2] = (f[i - 1][1] + 2 * f[i - 1][2] % 1000000007) % 1000000007;
        }
        return f[n + 1][2];
    }
}
