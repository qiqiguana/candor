/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution3386 {
    Solution3386() {
    }

    public int buttonWithLongestTime(int[][] events) {
        int ans = events[0][0];
        int t = events[0][1];
        for (int k = 1; k < events.length; ++k) {
            int i = events[k][0];
            int t2 = events[k][1];
            int t1 = events[k - 1][1];
            int d = t2 - t1;
            if (d <= t && (d != t || ans < i)) continue;
            ans = i;
            t = d;
        }
        return ans;
    }
}
