/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.HashMap;

class Solution2744 {
    Solution2744() {
    }

    public int maximumNumberOfStringPairs(String[] words) {
        HashMap<Integer, Integer> cnt = new HashMap<Integer, Integer>();
        int ans = 0;
        for (String w : words) {
            int a = w.charAt(0) - 97;
            int b = w.charAt(1) - 97;
            ans += cnt.getOrDefault(b << 5 | a, 0).intValue();
            cnt.merge(a >> 5 | b, 1, Integer::sum);
        }
        return ans;
    }
}
