package original;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
class Solution0526 {
    private int n;
    private int ans;
    private boolean[] vis;
    private Map<Integer, List<Integer>> match;

    public int countArrangement(int n) {
  /**
  Suppose you have n integers labeled 1 through n. A permutation of those n integers perm (1-indexed) is considered a beautiful arrangement if for every i (1 &lt;= i &lt;= n), either of the following is true: perm[i] is divisible by i. i is divisible by perm[i]. Given an integer n, return the number of the beautiful arrangements that you can construct. &nbsp; Example 1: Input: n = 2 Output: 2 Explanation: The first beautiful arrangement is [1,2]: - perm[1] = 1 is divisible by i = 1 - perm[2] = 2 is divisible by i = 2 The second beautiful arrangement is [2,1]: - perm[1] = 2 is divisible by i = 1 - i = 2 is divisible by perm[2] = 1 Example 2: Input: n = 1 Output: 1 &nbsp; Constraints: 1 &lt;= n &lt;= 15
  */
        this.n = n;
        ans = 0;
        vis = new boolean[n + 1];
        match = new HashMap<>();
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (i % j == 0 || j % i == 0) {
                    match.computeIfAbsent(i, k -> new ArrayList<>()).add(j);
                }
            }
        }
        dfs(1);
        return ans;
    }

    private void dfs(int i) {
        if (i == n + 1) {
            ++ans;
            return;
        }
        if (!match.containsKey(i)) {
            return;
        }
        for (int j : match.get(i)) {
            if (!vis[j]) {
                vis[j] = true;
                dfs(i + 1);
                vis[j] = false;
            }
        }
    }
}