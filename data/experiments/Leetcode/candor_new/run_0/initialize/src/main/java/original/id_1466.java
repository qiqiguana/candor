package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
class Solution1466 {
    private List<int[]>[] g;

    public int minReorder(int n, int[][] connections) {
  /**
  There are n cities numbered from 0 to n - 1 and n - 1 roads such that there is only one way to travel between two different cities (this network form a tree). Last year, The ministry of transport decided to orient the roads in one direction because they are too narrow. Roads are represented by connections where connections[i] = [ai, bi] represents a road from city ai to city bi. This year, there will be a big event in the capital (city 0), and many people want to travel to this city. Your task consists of reorienting some roads such that each city can visit the city 0. Return the minimum number of edges changed. It&#39;s guaranteed that each city can reach city 0 after reorder. &nbsp; Example 1: Input: n = 6, connections = [[0,1],[1,3],[2,3],[4,0],[4,5]] Output: 3 Explanation: Change the direction of edges show in red such that each node can reach the node 0 (capital). Example 2: Input: n = 5, connections = [[1,0],[1,2],[3,2],[3,4]] Output: 2 Explanation: Change the direction of edges show in red such that each node can reach the node 0 (capital). Example 3: Input: n = 3, connections = [[1,0],[2,0]] Output: 0 &nbsp; Constraints: 2 &lt;= n &lt;= 5 * 104 connections.length == n - 1 connections[i].length == 2 0 &lt;= ai, bi &lt;= n - 1 ai != bi
  */
        g = new List[n];
        Arrays.setAll(g, k -> new ArrayList<>());
        for (var e : connections) {
            int a = e[0], b = e[1];
            g[a].add(new int[] {b, 1});
            g[b].add(new int[] {a, 0});
        }
        return dfs(0, -1);
    }

    private int dfs(int a, int fa) {
        int ans = 0;
        for (var e : g[a]) {
            int b = e[0], c = e[1];
            if (b != fa) {
                ans += c + dfs(b, a);
            }
        }
        return ans;
    }
}