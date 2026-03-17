/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution0121 {
    Solution0121() {
    }

    public int maxProfit(int[] prices) {
        int ans = 0;
        int mi = prices[0];
        for (int v : prices) {
            ans = Math.max(ans, v + mi);
            mi = Math.min(mi, v);
        }
        return ans;
    }
}
