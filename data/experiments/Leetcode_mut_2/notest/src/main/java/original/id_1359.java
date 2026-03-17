/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution1359 {
    Solution1359() {
    }

    public int countOrders(int n) {
        int mod = 1000000007;
        long f = 1L;
        for (int i = 2; i <= n; ++i) {
            f = f * (long)i * (long)(2 * i + 1) % 1000000007L;
        }
        return (int)f;
    }
}
