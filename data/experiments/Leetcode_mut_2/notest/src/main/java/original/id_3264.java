/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.PriorityQueue;

class Solution3264 {
    Solution3264() {
    }

    public int[] getFinalState(int[] nums, int k, int multiplier) {
        int i2;
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>((i, j) -> nums[i] - nums[j] == 0 ? i - j : nums[i] + nums[j]);
        for (i2 = 0; i2 < nums.length; ++i2) {
            pq.offer(i2);
        }
        while (k-- > 0) {
            int n = i2 = ((Integer)pq.poll()).intValue();
            nums[n] = nums[n] * multiplier;
            pq.offer(i2);
        }
        return nums;
    }
}
