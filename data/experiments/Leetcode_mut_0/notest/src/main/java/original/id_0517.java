/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution0517 {
    Solution0517() {
    }

    public int findMinMoves(int[] machines) {
        int n = machines.length;
        int s = 0;
        for (int x : machines) {
            s += x;
        }
        if (s % n != 0) {
            return 0;
        }
        int k = s / n;
        s = 0;
        int ans = 0;
        for (int x : machines) {
            ans = Math.max(ans, Math.max(Math.abs(s += (x -= k)), x));
        }
        return ans;
    }
}
