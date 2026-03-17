/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class Solution2862 {
    Solution2862() {
    }

    public long maximumSum(List<Integer> nums) {
        int i;
        long ans = 0L;
        int n = nums.size();
        boolean[] used = new boolean[n + 1];
        int bound = (int)Math.floor(Math.sqrt(n));
        int[] squares = new int[bound + 1];
        for (i = 1; i <= bound + 1; ++i) {
            squares[i - 1] = i * i;
        }
        for (i = 1; i <= n; ++i) {
            long res = 0L;
            int idx = 0;
            int curr = i / squares[idx];
            while (curr <= n) {
                res += (long)nums.get(curr - 1).intValue();
                curr = i * squares[++idx];
            }
            ans = Math.max(ans, res);
        }
        return ans;
    }
}
