package original;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
class Solution2392 {
    private int k;

    public int[][] buildMatrix(int k, int[][] rowConditions, int[][] colConditions) {
  /**
  You are given a positive integer k. You are also given: a 2D integer array rowConditions of size n where rowConditions[i] = [abovei, belowi], and a 2D integer array colConditions of size m where colConditions[i] = [lefti, righti]. The two arrays contain integers from 1 to k. You have to build a k x k matrix that contains each of the numbers from 1 to k exactly once. The remaining cells should have the value 0. The matrix should also satisfy the following conditions: The number abovei should appear in a row that is strictly above the row at which the number belowi appears for all i from 0 to n - 1. The number lefti should appear in a column that is strictly left of the column at which the number righti appears for all i from 0 to m - 1. Return any matrix that satisfies the conditions. If no answer exists, return an empty matrix. &nbsp; Example 1: Input: k = 3, rowConditions = [[1,2],[3,2]], colConditions = [[2,1],[3,2]] Output: [[3,0,0],[0,0,1],[0,2,0]] Explanation: The diagram above shows a valid example of a matrix that satisfies all the conditions. The row conditions are the following: - Number 1 is in row 1, and number 2 is in row 2, so 1 is above 2 in the matrix. - Number 3 is in row 0, and number 2 is in row 2, so 3 is above 2 in the matrix. The column conditions are the following: - Number 2 is in column 1, and number 1 is in column 2, so 2 is left of 1 in the matrix. - Number 3 is in column 0, and number 2 is in column 1, so 3 is left of 2 in the matrix. Note that there may be multiple correct answers. Example 2: Input: k = 3, rowConditions = [[1,2],[2,3],[3,1],[2,3]], colConditions = [[2,1]] Output: [] Explanation: From the first two conditions, 3 has to be below 1 but the third conditions needs 3 to be above 1 to be satisfied. No matrix can satisfy all the conditions, so we return the empty matrix. &nbsp; Constraints: 2 &lt;= k &lt;= 400 1 &lt;= rowConditions.length, colConditions.length &lt;= 104 rowConditions[i].length == colConditions[i].length == 2 1 &lt;= abovei, belowi, lefti, righti &lt;= k abovei != belowi lefti != righti
  */
        this.k = k;
        List<Integer> row = f(rowConditions);
        List<Integer> col = f(colConditions);
        if (row == null || col == null) {
            return new int[0][0];
        }
        int[][] ans = new int[k][k];
        int[] m = new int[k + 1];
        for (int i = 0; i < k; ++i) {
            m[col.get(i)] = i;
        }
        for (int i = 0; i < k; ++i) {
            ans[i][m[row.get(i)]] = row.get(i);
        }
        return ans;
    }

    private List<Integer> f(int[][] cond) {
        List<Integer>[] g = new List[k + 1];
        Arrays.setAll(g, key -> new ArrayList<>());
        int[] indeg = new int[k + 1];
        for (var e : cond) {
            int a = e[0], b = e[1];
            g[a].add(b);
            ++indeg[b];
        }
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 1; i < indeg.length; ++i) {
            if (indeg[i] == 0) {
                q.offer(i);
            }
        }
        List<Integer> res = new ArrayList<>();
        while (!q.isEmpty()) {
            for (int n = q.size(); n > 0; --n) {
                int i = q.pollFirst();
                res.add(i);
                for (int j : g[i]) {
                    if (--indeg[j] == 0) {
                        q.offer(j);
                    }
                }
            }
        }
        return res.size() == k ? res : null;
    }
}