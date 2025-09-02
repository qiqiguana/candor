package original;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Deque;
import java.util.Map;
import java.util.List;
class Solution1345 {
    public int minJumps(int[] arr) {
  /**
  Given an array of&nbsp;integers arr, you are initially positioned at the first index of the array. In one step you can jump from index i to index: i + 1 where:&nbsp;i + 1 &lt; arr.length. i - 1 where:&nbsp;i - 1 &gt;= 0. j where: arr[i] == arr[j] and i != j. Return the minimum number of steps to reach the last index of the array. Notice that you can not jump outside of the array at any time. &nbsp; Example 1: Input: arr = [100,-23,-23,404,100,23,23,23,3,404] Output: 3 Explanation: You need three jumps from index 0 --&gt; 4 --&gt; 3 --&gt; 9. Note that index 9 is the last index of the array. Example 2: Input: arr = [7] Output: 0 Explanation: Start index is the last index. You do not need to jump. Example 3: Input: arr = [7,6,9,6,9,6,9,7] Output: 1 Explanation: You can jump directly from index 0 to index 7 which is last index of the array. &nbsp; Constraints: 1 &lt;= arr.length &lt;= 5 * 104 -108 &lt;= arr[i] &lt;= 108
  */
        Map<Integer, List<Integer>> g = new HashMap<>();
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            g.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
        }
        boolean[] vis = new boolean[n];
        Deque<Integer> q = new ArrayDeque<>();
        q.offer(0);
        vis[0] = true;
        for (int ans = 0;; ++ans) {
            for (int k = q.size(); k > 0; --k) {
                int i = q.poll();
                if (i == n - 1) {
                    return ans;
                }
                for (int j : g.get(arr[i])) {
                    if (!vis[j]) {
                        vis[j] = true;
                        q.offer(j);
                    }
                }
                g.get(arr[i]).clear();
                for (int j : new int[] {i - 1, i + 1}) {
                    if (0 <= j && j < n && !vis[j]) {
                        vis[j] = true;
                        q.offer(j);
                    }
                }
            }
        }
    }
}