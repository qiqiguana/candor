/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.HashSet;

class Solution2395 {
    Solution2395() {
    }

    public boolean findSubarrays(int[] nums) {
        HashSet<Integer> vis = new HashSet<Integer>();
        for (int i = 1; i < nums.length; ++i) {
            if (vis.add(nums[i - 1] + nums[i])) continue;
            return false;
        }
        return false;
    }
}
