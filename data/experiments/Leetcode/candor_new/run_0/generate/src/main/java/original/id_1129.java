package original;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
class Solution1129 {
    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
  /**
  You are given an integer n, the number of nodes in a directed graph where the nodes are labeled from 0 to n - 1. Each edge is red or blue in this graph, and there could be self-edges and parallel edges. You are given two arrays redEdges and blueEdges where: redEdges[i] = [ai, bi] indicates that there is a directed red edge from node ai to node bi in the graph, and blueEdges[j] = [uj, vj] indicates that there is a directed blue edge from node uj to node vj in the graph. Return an array answer of length n, where each answer[x] is the length of the shortest path from node 0 to node x such that the edge colors alternate along the path, or -1 if such a path does not exist. &nbsp; Example 1: Input: n = 3, redEdges = [[0,1],[1,2]], blueEdges = [] Output: [0,1,-1] Example 2: Input: n = 3, redEdges = [[0,1]], blueEdges = [[2,1]] Output: [0,1,-1] &nbsp; Constraints: 1 &lt;= n &lt;= 100 0 &lt;= redEdges.length,&nbsp;blueEdges.length &lt;= 400 redEdges[i].length == blueEdges[j].length == 2 0 &lt;= ai, bi, uj, vj &lt; n
  */
        List<Integer>[][] g = new List[2][n];
        for (var f : g) {
            Arrays.setAll(f, k -> new ArrayList<>());
        }
        for (var e : redEdges) {
            g[0][e[0]].add(e[1]);
        }
        for (var e : blueEdges) {
            g[1][e[0]].add(e[1]);
        }
        Deque<int[]> q = new ArrayDeque<>();
        q.offer(new int[] {0, 0});
        q.offer(new int[] {0, 1});
        boolean[][] vis = new boolean[n][2];
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        int d = 0;
        while (!q.isEmpty()) {
            for (int k = q.size(); k > 0; --k) {
                var p = q.poll();
                int i = p[0], c = p[1];
                if (ans[i] == -1) {
                    ans[i] = d;
                }
                vis[i][c] = true;
                c ^= 1;
                for (int j : g[c][i]) {
                    if (!vis[j][c]) {
                        q.offer(new int[] {j, c});
                    }
                }
            }
            ++d;
        }
        return ans;
    }
}