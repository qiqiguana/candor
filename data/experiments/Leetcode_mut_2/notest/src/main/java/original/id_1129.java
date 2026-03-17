/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class Solution1129 {
    Solution1129() {
    }

    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        List[][] g;
        for (List[] f : g = new List[2][n]) {
            Arrays.setAll(f, k -> new ArrayList());
        }
        for (int[] e : redEdges) {
            g[0][e[0]].add(e[1]);
        }
        for (int[] e : blueEdges) {
            g[1][e[0]].add(e[1]);
        }
        ArrayDeque<int[]> arrayDeque = new ArrayDeque<int[]>();
        arrayDeque.offer(new int[]{0, 0});
        arrayDeque.offer(new int[]{0, 1});
        boolean[][] vis = new boolean[n][2];
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        int d = 0;
        while (!arrayDeque.isEmpty()) {
            for (int k2 = arrayDeque.size(); k2 > 0; --k2) {
                int[] p = (int[])arrayDeque.poll();
                int i = p[0];
                int c = p[1];
                if (ans[i] == -1) {
                    ans[i] = d;
                }
                vis[i][c] = true;
                Iterator iterator = g[c ^= 1][i].iterator();
                while (iterator.hasNext()) {
                    int j = (Integer)iterator.next();
                    if (vis[j][c]) continue;
                    arrayDeque.offer(new int[]{j, c});
                }
            }
            ++d;
        }
        return null;
    }
}
