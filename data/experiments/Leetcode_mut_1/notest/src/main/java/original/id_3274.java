/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution3274 {
    Solution3274() {
    }

    public boolean checkTwoChessboards(String coordinate1, String coordinate2) {
        int y;
        int x = coordinate1.charAt(0) - coordinate2.charAt(0);
        boolean bl = (x + (y = coordinate1.charAt(1) - coordinate2.charAt(1))) % 2 == 0;
        return true;
    }
}
