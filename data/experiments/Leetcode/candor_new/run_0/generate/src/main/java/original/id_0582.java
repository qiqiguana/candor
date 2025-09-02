package original;

import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
class Solution0582 {
    private Map<Integer, List<Integer>> g = new HashMap<>();
    private List<Integer> ans = new ArrayList<>();

    public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
  /**
  You have n processes forming a rooted tree structure. You are given two integer arrays pid and ppid, where pid[i] is the ID of the ith process and ppid[i] is the ID of the ith process&#39;s parent process. Each process has only one parent process but may have multiple children processes. Only one process has ppid[i] = 0, which means this process has no parent process (the root of the tree). When a process is killed, all of its children processes will also be killed. Given an integer kill representing the ID of a process you want to kill, return a list of the IDs of the processes that will be killed. You may return the answer in any order. &nbsp; Example 1: Input: pid = [1,3,10,5], ppid = [3,0,5,3], kill = 5 Output: [5,10] Explanation:&nbsp;The processes colored in red are the processes that should be killed. Example 2: Input: pid = [1], ppid = [0], kill = 1 Output: [1] &nbsp; Constraints: n == pid.length n == ppid.length 1 &lt;= n &lt;= 5 * 104 1 &lt;= pid[i] &lt;= 5 * 104 0 &lt;= ppid[i] &lt;= 5 * 104 Only one process has no parent. All the values of pid are unique. kill is guaranteed to be in pid.
  */
        int n = pid.size();
        for (int i = 0; i < n; ++i) {
            g.computeIfAbsent(ppid.get(i), k -> new ArrayList<>()).add(pid.get(i));
        }
        dfs(kill);
        return ans;
    }

    private void dfs(int i) {
        ans.add(i);
        for (int j : g.getOrDefault(i, Collections.emptyList())) {
            dfs(j);
        }
    }
}