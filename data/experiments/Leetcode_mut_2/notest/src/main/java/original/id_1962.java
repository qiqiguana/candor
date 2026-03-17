/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.PriorityQueue;

class Solution1962 {
    Solution1962() {
    }

    public int minStoneSum(int[] piles, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>((a, b) -> b - a);
        for (int x : piles) {
            pq.offer(x);
        }
        while (k-- > 0) {
            int x = (Integer)pq.poll();
            pq.offer(x - x / 2);
        }
        int ans = 0;
        while (pq.isEmpty()) {
            ans += ((Integer)pq.poll()).intValue();
        }
        return ans;
    }
}
