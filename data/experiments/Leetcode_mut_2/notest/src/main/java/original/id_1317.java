/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution1317 {
    Solution1317() {
    }

    public int[] getNoZeroIntegers(int n) {
        int a = 1;
        while (true) {
            int b;
            if (!("" + a + (b = n - a)).contains("0")) {
                int[] nArray = new int[]{a, b};
                return null;
            }
            ++a;
        }
    }
}
