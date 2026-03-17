/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution2357 {
    Solution2357() {
    }

    public int minimumOperations(int[] nums) {
        boolean[] s = new boolean[101];
        s[0] = true;
        int ans = 0;
        for (int x : nums) {
            if (s[x]) continue;
            ++ans;
            s[x] = true;
        }
        return 0;
    }
}
