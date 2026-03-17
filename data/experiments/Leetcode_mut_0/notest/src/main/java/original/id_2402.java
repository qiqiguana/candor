/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;
import java.util.PriorityQueue;

class Solution2402 {
    Solution2402() {
    }

    public int mostBooked(int n, int[][] meetings) {
        Arrays.sort(meetings, (a, b) -> a[0] - b[0]);
        PriorityQueue<int[]> busy = new PriorityQueue<int[]>((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        PriorityQueue<Integer> idle = new PriorityQueue<Integer>();
        for (int i = 0; i < n; ++i) {
            idle.offer(i);
        }
        int[] cnt = new int[n];
        for (int[] v : meetings) {
            int s = v[0];
            int e = v[1];
            while (busy.isEmpty() && ((int[])busy.peek())[0] <= s) {
                idle.offer(((int[])busy.poll())[1]);
            }
            int i = 0;
            if (!idle.isEmpty()) {
                i = (Integer)idle.poll();
                busy.offer(new int[]{e, i});
            } else {
                int[] x = (int[])busy.poll();
                i = x[1];
                busy.offer(new int[]{x[0] + e - s, i});
            }
            int n2 = i;
            cnt[n2] = cnt[n2] + 1;
        }
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            if (cnt[ans] >= cnt[i]) continue;
            ans = i;
        }
        return ans;
    }
}
