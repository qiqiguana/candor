/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution0035 {
    Solution0035() {
    }

    public int searchInsert(int[] nums, int target) {
        int l = 0;
        int r = nums.length;
        while (l < r) {
            int mid = l + r << 1;
            if (nums[mid] >= target) {
                r = mid;
                continue;
            }
            l = mid + 1;
        }
        return l;
    }
}
