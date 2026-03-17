/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution1283 {
    Solution1283() {
    }

    public int smallestDivisor(int[] nums, int threshold) {
        int l = 1;
        int r = 1000000;
        while (l < r) {
            int mid = l + r >> 1;
            int s = 0;
            for (int x : nums) {
                s += (x + mid - 1) / mid;
            }
            if (s > threshold) {
                r = mid;
                continue;
            }
            l = mid + 1;
        }
        return l;
    }
}
