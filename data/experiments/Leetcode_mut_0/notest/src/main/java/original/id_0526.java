/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution0526 {
    private int n;
    private int ans;
    private boolean[] vis;
    private Map<Integer, List<Integer>> match;

    Solution0526() {
    }

    public int countArrangement(int n) {
        this.n = n;
        this.ans = 0;
        this.vis = new boolean[n + 1];
        this.match = new HashMap<Integer, List<Integer>>();
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (i % j != 0 && j % i != 0) continue;
                this.match.computeIfAbsent(i, k -> new ArrayList()).add(j);
            }
        }
        return this.ans;
    }

    private void dfs(int i) {
        if (i == this.n + 1) {
            ++this.ans;
            return;
        }
        if (!this.match.containsKey(i)) {
            return;
        }
        for (int j : this.match.get(i)) {
            if (this.vis[j]) continue;
            this.vis[j] = true;
            this.dfs(i + 1);
            this.vis[j] = false;
        }
    }
}
