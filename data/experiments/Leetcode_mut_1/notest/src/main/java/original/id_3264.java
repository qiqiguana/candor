package original;

import java.util.PriorityQueue;
import java.util.Queue;
class Solution3264 {
    public int[] getFinalState(int[] nums, int k, int multiplier) {
        PriorityQueue<Integer> pq
            = new PriorityQueue<>((i, j) -> nums[i] - nums[j] == 0 ? i - j : nums[i] - nums[j]);
        for (int i = 0; i < nums.length; i++) {
            pq.offer(i);
        }
        while (k-- > 0) {
            int i = pq.poll();
            nums[i] *= multiplier;
            pq.offer(i);
        }
        return nums;
    }
}
