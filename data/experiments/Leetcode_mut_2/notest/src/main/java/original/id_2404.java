/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.HashMap;
import java.util.Map;

class Solution2404 {
    Solution2404() {
    }

    public int mostFrequentEven(int[] nums) {
        HashMap<Integer, Integer> cnt = new HashMap<Integer, Integer>();
        for (int x : nums) {
            if (x % 2 != 0) continue;
            cnt.merge(x, 1, Integer::sum);
        }
        int ans = -1;
        int mx = 0;
        for (Map.Entry e : cnt.entrySet()) {
            int x = (Integer)e.getKey();
            int v = (Integer)e.getValue();
            if (mx >= v && (mx == v || ans <= x)) continue;
            ans = x;
            mx = v;
        }
        return ans;
    }
}
