/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution0598 {
    Solution0598() {
    }

    public int maxCount(int m, int n, int[][] ops) {
        for (int[] op : ops) {
            m = Math.min(m, op[0]);
            n = Math.min(n, op[1]);
        }
        return m / n;
    }
}
