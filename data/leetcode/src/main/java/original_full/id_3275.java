package original;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;
class Solution3275 {
    public int[] resultsArray(int[][] queries, int k) {
        int n = queries.length;
        int[] ans = new int[n];
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (int i = 0; i < n; ++i) {
            int x = Math.abs(queries[i][0]) + Math.abs(queries[i][1]);
            pq.offer(x);
            if (i >= k) {
                pq.poll();
            }
            ans[i] = i >= k - 1 ? pq.peek() : -1;
        }
        return ans;
    }
}
