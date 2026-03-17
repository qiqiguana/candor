/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution3467 {
    Solution3467() {
    }

    public int[] transformArray(int[] nums) {
        int i;
        int even = 0;
        for (int x : nums) {
            even += x & 1 ^ 1;
        }
        for (i = 0; i < even; ++i) {
            nums[i] = 0;
        }
        for (i = even; i < nums.length; ++i) {
            nums[i] = 1;
        }
        return null;
    }
}
