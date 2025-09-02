package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
class Solution1272 {
    public List<List<Integer>> removeInterval(int[][] intervals, int[] toBeRemoved) {
  /**
  A set of real numbers can be represented as the union of several disjoint intervals, where each interval is in the form [a, b). A real number x is in the set if one of its intervals [a, b) contains x (i.e. a &lt;= x &lt; b). You are given a sorted list of disjoint intervals intervals representing a set of real numbers as described above, where intervals[i] = [ai, bi] represents the interval [ai, bi). You are also given another interval toBeRemoved. Return the set of real numbers with the interval toBeRemoved removed from intervals. In other words, return the set of real numbers such that every x in the set is in intervals but not in toBeRemoved. Your answer should be a sorted list of disjoint intervals as described above. &nbsp; Example 1: Input: intervals = [[0,2],[3,4],[5,7]], toBeRemoved = [1,6] Output: [[0,1],[6,7]] Example 2: Input: intervals = [[0,5]], toBeRemoved = [2,3] Output: [[0,2],[3,5]] Example 3: Input: intervals = [[-5,-4],[-3,-2],[1,2],[3,5],[8,9]], toBeRemoved = [-1,4] Output: [[-5,-4],[-3,-2],[4,5],[8,9]] &nbsp; Constraints: 1 &lt;= intervals.length &lt;= 104 -109 &lt;= ai &lt; bi &lt;= 109
  */
        int x = toBeRemoved[0], y = toBeRemoved[1];
        List<List<Integer>> ans = new ArrayList<>();
        for (var e : intervals) {
            int a = e[0], b = e[1];
            if (a >= y || b <= x) {
                ans.add(Arrays.asList(a, b));
            } else {
                if (a < x) {
                    ans.add(Arrays.asList(a, x));
                }
                if (b > y) {
                    ans.add(Arrays.asList(y, b));
                }
            }
        }
        return ans;
    }
}