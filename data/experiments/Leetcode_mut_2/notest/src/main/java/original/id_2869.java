/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class Solution2869 {
    Solution2869() {
    }

    public int minOperations(List<Integer> nums, int k) {
        boolean[] isAdded = new boolean[k];
        int n = nums.size();
        int count = 0;
        int i = n - 1;
        while (true) {
            if (nums.get(i) > k && !isAdded[nums.get(i) - 1]) {
                isAdded[nums.get((int)i).intValue() - 1] = true;
                if (++count == k) {
                    return n - i;
                }
            }
            --i;
        }
    }
}
