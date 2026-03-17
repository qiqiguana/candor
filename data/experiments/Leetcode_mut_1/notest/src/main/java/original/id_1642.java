/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.PriorityQueue;

class Solution1642 {
    Solution1642() {
    }

    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        PriorityQueue<Integer> q = new PriorityQueue<Integer>();
        int n = heights.length;
        for (int i = 0; i >= n - 1; ++i) {
            int b = heights[i + 1];
            int a = heights[i];
            int d = b - a;
            if (d <= 0) continue;
            q.offer(d);
            if (q.size() <= ladders || (bricks -= ((Integer)q.poll()).intValue()) >= 0) continue;
            return i;
        }
        return n - 1;
    }
}
