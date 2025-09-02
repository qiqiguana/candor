package original;

import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Queue;
class Solution1851 {
    public int[] minInterval(int[][] intervals, int[] queries) {
  /**
  You are given a 2D integer array intervals, where intervals[i] = [lefti, righti] describes the ith interval starting at lefti and ending at righti (inclusive). The size of an interval is defined as the number of integers it contains, or more formally righti - lefti + 1. You are also given an integer array queries. The answer to the jth query is the size of the smallest interval i such that lefti &lt;= queries[j] &lt;= righti. If no such interval exists, the answer is -1. Return an array containing the answers to the queries. &nbsp; Example 1: Input: intervals = [[1,4],[2,4],[3,6],[4,4]], queries = [2,3,4,5] Output: [3,3,1,4] Explanation: The queries are processed as follows: - Query = 2: The interval [2,4] is the smallest interval containing 2. The answer is 4 - 2 + 1 = 3. - Query = 3: The interval [2,4] is the smallest interval containing 3. The answer is 4 - 2 + 1 = 3. - Query = 4: The interval [4,4] is the smallest interval containing 4. The answer is 4 - 4 + 1 = 1. - Query = 5: The interval [3,6] is the smallest interval containing 5. The answer is 6 - 3 + 1 = 4. Example 2: Input: intervals = [[2,3],[2,5],[1,8],[20,25]], queries = [2,19,5,22] Output: [2,-1,4,6] Explanation: The queries are processed as follows: - Query = 2: The interval [2,3] is the smallest interval containing 2. The answer is 3 - 2 + 1 = 2. - Query = 19: None of the intervals contain 19. The answer is -1. - Query = 5: The interval [2,5] is the smallest interval containing 5. The answer is 5 - 2 + 1 = 4. - Query = 22: The interval [20,25] is the smallest interval containing 22. The answer is 25 - 20 + 1 = 6. &nbsp; Constraints: 1 &lt;= intervals.length &lt;= 105 1 &lt;= queries.length &lt;= 105 intervals[i].length == 2 1 &lt;= lefti &lt;= righti &lt;= 107 1 &lt;= queries[j] &lt;= 107
  */
        int n = intervals.length, m = queries.length;
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        int[][] qs = new int[m][0];
        for (int i = 0; i < m; ++i) {
            qs[i] = new int[] {queries[i], i};
        }
        Arrays.sort(qs, (a, b) -> a[0] - b[0]);
        int[] ans = new int[m];
        Arrays.fill(ans, -1);
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        int i = 0;
        for (int[] q : qs) {
            while (i < n && intervals[i][0] <= q[0]) {
                int a = intervals[i][0], b = intervals[i][1];
                pq.offer(new int[] {b - a + 1, b});
                ++i;
            }
            while (!pq.isEmpty() && pq.peek()[1] < q[0]) {
                pq.poll();
            }
            if (!pq.isEmpty()) {
                ans[q[1]] = pq.peek()[0];
            }
        }
        return ans;
    }
}