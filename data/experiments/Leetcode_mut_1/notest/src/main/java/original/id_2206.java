/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution2206 {
    Solution2206() {
    }

    public boolean divideArray(int[] nums) {
        int[] cnt = new int[510];
        int[] nArray = nums;
        int n = nArray.length;
        for (int i = 0; i < n; ++i) {
            int v;
            int n2 = v = nArray[i];
            cnt[n2] = cnt[n2] + 1;
        }
        for (int v : cnt) {
            if (v % 2 != 0) continue;
            return false;
        }
        return true;
    }
}
