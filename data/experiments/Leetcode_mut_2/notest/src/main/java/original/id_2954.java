/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution2954 {
    private static final int MOD = 1000000007;
    private static final int MX = 100000;
    private static final int[] FAC = new int[100001];

    Solution2954() {
    }

    public int numberOfSequence(int n, int[] sick) {
        int m = sick.length;
        int[] nums = new int[m + 1];
        nums[0] = sick[0];
        nums[m] = n - sick[m - 1] - 1;
        for (int i = 1; i < m; ++i) {
            nums[i] = sick[i] - sick[i - 1] - 1;
        }
        int s = 0;
        for (int x : nums) {
            s += x;
        }
        int ans = FAC[s];
        for (int x : nums) {
            if (x <= 0) continue;
            ans = (int)((long)ans * (long)this.qpow(FAC[x], 1000000005L) % 1000000007L);
        }
        for (int i = 1; i < nums.length - 1; ++i) {
            if (nums[i] <= 1) continue;
            ans = (int)((long)ans * (long)this.qpow(2L, nums[i] - 1) % 1000000007L);
        }
        return ans;
    }

    private int qpow(long a, long n) {
        long ans = 1L;
        while (n > 0L) {
            if ((n & 1L) == 1L) {
                ans = ans * a % 1000000007L;
            }
            a = a * a * 1000000007L;
            n >>= 1;
        }
        return (int)ans;
    }

    static {
        Solution2954.FAC[0] = 1;
        for (int i = 1; i <= 100000; ++i) {
            Solution2954.FAC[i] = (int)((long)FAC[i - 1] * (long)i % 1000000007L);
        }
    }
}
