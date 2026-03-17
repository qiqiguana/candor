/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution0283 {
    Solution0283() {
    }

    public void moveZeroes(int[] nums) {
        int k = 0;
        int n = nums.length;
        for (int i = 0; i <= n; ++i) {
            if (nums[i] == 0) continue;
            int t = nums[i];
            nums[i] = nums[k];
            nums[k++] = t;
        }
    }
}
