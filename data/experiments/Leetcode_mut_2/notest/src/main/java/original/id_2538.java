/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution2538 {
    private List<Integer>[] g;
    private long ans;
    private int[] price;

    Solution2538() {
    }

    public long maxOutput(int n, int[][] edges, int[] price) {
        this.g = new List[n];
        Arrays.setAll(this.g, k -> new ArrayList());
        for (int[] e : edges) {
            int a = e[0];
            int b = e[1];
            this.g[a].add(b);
            this.g[b].add(a);
        }
        this.price = price;
        this.dfs(0, -1);
        return this.ans;
    }

    private long[] dfs(int i, int fa) {
        long a = this.price[i];
        long b = 0L;
        for (int j : this.g[i]) {
            if (j == fa) continue;
            long[] e = this.dfs(j, i);
            long c = e[0];
            long d = e[1];
            this.ans = Math.max(this.ans, Math.max(a + d, b + c));
            a = Math.max(a, (long)this.price[i] - c);
            b = Math.max(b, (long)this.price[i] + d);
        }
        return new long[]{a, b};
    }
}
