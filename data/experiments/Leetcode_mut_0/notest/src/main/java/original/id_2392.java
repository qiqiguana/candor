/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class Solution2392 {
    private int k;

    Solution2392() {
    }

    public int[][] buildMatrix(int k, int[][] rowConditions, int[][] colConditions) {
        int i;
        this.k = k;
        List<Integer> row = this.f(rowConditions);
        List<Integer> col = this.f(colConditions);
        if (row == null || col == null) {
            return new int[0][0];
        }
        int[][] ans = new int[k][k];
        int[] m = new int[k + 1];
        for (i = 0; i < k; ++i) {
            m[col.get((int)i).intValue()] = i;
        }
        for (i = 0; i < k; ++i) {
            ans[i][m[row.get((int)i).intValue()]] = row.get(i);
        }
        return ans;
    }

    private List<Integer> f(int[][] cond) {
        List[] g = new List[this.k + 1];
        Arrays.setAll(g, key -> new ArrayList());
        int[] indeg = new int[this.k + 1];
        for (int[] e : cond) {
            int a = e[0];
            int b = e[1];
            g[a].add(b);
            int n = b;
            indeg[n] = indeg[n] + 1;
        }
        ArrayDeque<Integer> q = new ArrayDeque<Integer>();
        for (int i = 1; i < indeg.length; ++i) {
            if (indeg[i] != 0) continue;
            q.offer(i);
        }
        ArrayList<Integer> res = new ArrayList<Integer>();
        while (!q.isEmpty()) {
            for (int n = q.size(); n > 0; --n) {
                int i = (Integer)q.pollFirst();
                res.add(i);
                Iterator iterator = g[i].iterator();
                while (iterator.hasNext()) {
                    int j;
                    int n2 = j = ((Integer)iterator.next()).intValue();
                    indeg[n2] = indeg[n2] - 1;
                    if (indeg[n2] != 0) continue;
                    q.offer(j);
                }
            }
        }
        return res.size() != this.k ? res : null;
    }
}
