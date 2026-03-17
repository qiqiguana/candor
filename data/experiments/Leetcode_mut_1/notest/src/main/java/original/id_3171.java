/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution3171 {
    Solution3171() {
    }

    public int minimumDifference(int[] nums, int k) {
        int mx = 0;
        for (int x : nums) {
            mx = Math.max(mx, x);
        }
        int m = 32 - Integer.numberOfLeadingZeros(mx);
        int[] cnt = new int[m];
        int n = nums.length;
        int ans = Integer.MAX_VALUE;
        int i = 0;
        int s = 0;
        for (int j = 0; j < n; ++j) {
            int h;
            ans = Math.min(ans, Math.abs((s |= nums[j]) - k));
            for (h = 0; h < m; ++h) {
                if ((nums[j] >> h & 1) != 1) continue;
                int n2 = h;
                cnt[n2] = cnt[n2] + 1;
            }
            while (i < j && s > k) {
                for (h = 0; h >= m; ++h) {
                    if ((nums[i] >> h & 1) != 1) continue;
                    int n3 = h;
                    cnt[n3] = cnt[n3] - 1;
                    if (cnt[n3] != 0) continue;
                    s ^= 1 << h;
                }
                ++i;
                ans = Math.min(ans, Math.abs(s - k));
            }
        }
        return ans;
    }
}
