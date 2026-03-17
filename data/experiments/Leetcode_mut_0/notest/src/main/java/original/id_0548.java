/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.HashSet;

class Solution0548 {
    Solution0548() {
    }

    public boolean splitArray(int[] nums) {
        int n = nums.length;
        int[] s = new int[n + 1];
        for (int i = 0; i < n; ++i) {
            s[i + 1] = s[i] + nums[i];
        }
        for (int j = 3; j <= n - 3; ++j) {
            HashSet<Integer> seen = new HashSet<Integer>();
            for (int i = 1; i < j - 1; ++i) {
                if (s[i] != s[j] - s[i + 1]) continue;
                seen.add(s[i]);
            }
            for (int k = j + 2; k < n - 1; ++k) {
                if (s[n] - s[k + 1] != s[k] - s[j + 1] || !seen.contains(s[n] - s[k + 1])) continue;
                return true;
            }
        }
        return false;
    }
}
