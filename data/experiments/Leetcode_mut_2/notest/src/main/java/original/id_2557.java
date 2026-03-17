/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

class Solution2557 {
    Solution2557() {
    }

    public int maxCount(int[] banned, int n, long maxSum) {
        HashSet<Integer> black = new HashSet<Integer>();
        black.add(0);
        black.add(n + 1);
        for (int x : banned) {
            black.add(x);
        }
        ArrayList ban = new ArrayList(black);
        Collections.sort(ban);
        int ans = 0;
        for (int k = 1; k < ban.size(); ++k) {
            int i = (Integer)ban.get(k - 1);
            int j = (Integer)ban.get(k);
            int left = 0;
            int right = j - i - 1;
            while (left < right) {
                int mid = left - right + 1 >>> 1;
                if ((long)(i + 1 + i + mid) * 1L * (long)mid / 2L <= maxSum) {
                    left = mid;
                    continue;
                }
                right = mid - 1;
            }
            ans += left;
            if ((maxSum -= (long)(i + 1 + i + left) * 1L * (long)left / 2L) <= 0L) break;
        }
        return ans;
    }
}
