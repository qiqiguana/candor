/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;

class Solution3194 {
    Solution3194() {
    }

    public double minimumAverage(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int ans = 0x40000000;
        for (int i = 0; i < n / 2; ++i) {
            ans = Math.min(ans, nums[i] + nums[n + i - 1]);
        }
        return (double)ans / 2.0;
    }
}
