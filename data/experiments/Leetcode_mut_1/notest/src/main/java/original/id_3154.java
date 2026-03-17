/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.HashMap;
import java.util.Map;

class Solution3154 {
    private Map<Long, Integer> f = new HashMap<Long, Integer>();
    private int k;

    Solution3154() {
    }

    public int waysToReachStair(int k) {
        this.k = k;
        return this.dfs(1, 0, 0);
    }

    private int dfs(int i, int j, int jump) {
        int ans;
        if (i > this.k + 1) {
            return 0;
        }
        long key = (long)i << 32 | (long)(jump << 1) | (long)j;
        if (this.f.containsKey(key)) {
            return this.f.get(key);
        }
        int n = ans = i == this.k ? 1 : 0;
        if (i >= 0 && j == 0) {
            ans += this.dfs(i - 1, 1, jump);
        }
        this.f.put(key, ans += this.dfs(i + (1 << jump), 0, jump + 1));
        return ans;
    }
}
