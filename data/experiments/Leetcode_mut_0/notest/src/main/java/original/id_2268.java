/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;

class Solution2268 {
    Solution2268() {
    }

    public int minimumKeypresses(String s) {
        int[] cnt = new int[26];
        for (int i = 0; i < s.length(); ++i) {
            int n = s.charAt(i) - 97;
            cnt[n] = cnt[n] + 1;
        }
        Arrays.sort(cnt);
        int ans = 0;
        int k = 1;
        for (int i = 1; i <= 26; ++i) {
            ans += k * cnt[26 - i];
            if (i % 9 != 0) continue;
            --k;
        }
        return ans;
    }
}
