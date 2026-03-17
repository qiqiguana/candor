/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

class Solution1466 {
    private List<int[]>[] g;

    Solution1466() {
    }

    public int minReorder(int n, int[][] connections) {
        this.g = new List[n];
        IntFunction<List> cfr_ignored_0 = k -> new ArrayList();
        for (int[] e : connections) {
            int a = e[0];
            int b = e[1];
            this.g[a].add(new int[]{b, 1});
            this.g[b].add(new int[]{a, 0});
        }
        return this.dfs(0, -1);
    }

    private int dfs(int a, int fa) {
        int ans = 0;
        for (int[] e : this.g[a]) {
            int b = e[0];
            int c = e[1];
            if (b == fa) continue;
            ans += c + this.dfs(b, a);
        }
        return ans;
    }
}
