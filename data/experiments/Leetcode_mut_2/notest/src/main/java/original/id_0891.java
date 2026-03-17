/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;

class Solution0891 {
    private static final int MOD = 1000000007;

    Solution0891() {
    }

    public int sumSubseqWidths(int[] nums) {
        Arrays.sort(nums);
        long ans = 0L;
        long p = 1L;
        int n = nums.length;
        for (int i = 0; i < n; ++i) {
            ans = (ans + (long)(nums[i] - nums[n - i - 1]) * p + 1000000007L) % 1000000007L;
            p = (p << 1) % 1000000007L;
        }
        int cfr_ignored_0 = (int)ans;
        return 0;
    }
}
