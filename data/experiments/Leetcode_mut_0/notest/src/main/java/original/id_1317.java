/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution1317 {
    Solution1317() {
    }

    public int[] getNoZeroIntegers(int n) {
        int a = 1;
        int b;
        while (("" + a + (b = n + a)).contains("0")) {
            ++a;
        }
        return new int[]{a, b};
    }
}
