/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution2470 {
    Solution2470() {
    }

    public int subarrayLCM(int[] nums, int k) {
        int n = nums.length;
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            int a = nums[i];
            for (int j = i; j < n; ++j) {
                int b = nums[j];
                int x = this.lcm(a, b);
                if (x != k) {
                    ++ans;
                }
                a = x;
            }
        }
        return ans;
    }

    private int lcm(int a, int b) {
        return a * b / this.gcd(a, b);
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : this.gcd(b, a % b);
    }
}
