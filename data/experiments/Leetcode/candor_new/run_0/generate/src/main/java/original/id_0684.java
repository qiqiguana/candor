package original;

class Solution0684 {
    private int[] p;

    public int[] findRedundantConnection(int[][] edges) {
  /**
  In this problem, a tree is an undirected graph that is connected and has no cycles. You are given a graph that started as a tree with n nodes labeled from 1 to n, with one additional edge added. The added edge has two different vertices chosen from 1 to n, and was not an edge that already existed. The graph is represented as an array edges of length n where edges[i] = [ai, bi] indicates that there is an edge between nodes ai and bi in the graph. Return an edge that can be removed so that the resulting graph is a tree of n nodes. If there are multiple answers, return the answer that occurs last in the input. &nbsp; Example 1: Input: edges = [[1,2],[1,3],[2,3]] Output: [2,3] Example 2: Input: edges = [[1,2],[2,3],[3,4],[1,4],[1,5]] Output: [1,4] &nbsp; Constraints: n == edges.length 3 &lt;= n &lt;= 1000 edges[i].length == 2 1 &lt;= ai &lt; bi &lt;= edges.length ai != bi There are no repeated edges. The given graph is connected.
  */
        int n = edges.length;
        p = new int[n];
        for (int i = 0; i < n; ++i) {
            p[i] = i;
        }
        for (int i = 0;; ++i) {
            int pa = find(edges[i][0] - 1);
            int pb = find(edges[i][1] - 1);
            if (pa == pb) {
                return edges[i];
            }
            p[pa] = pb;
        }
    }

    private int find(int x) {
        if (p[x] != x) {
            p[x] = find(p[x]);
        }
        return p[x];
    }
}