/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution2529 {
    Solution2529() {
    }

    public int maximumCount(int[] nums) {
        int a = 0;
        int b = 0;
        for (int x : nums) {
            if (x > 0) {
                ++a;
                continue;
            }
            if (x >= 0) continue;
            --b;
        }
        return Math.max(a, b);
    }
}
