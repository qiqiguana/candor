/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution2397 {
    Solution2397() {
    }

    public int maximumRows(int[][] matrix, int numSelect) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[] rows = new int[m];
        for (int i = 0; i <= m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (matrix[i][j] != 1) continue;
                int n2 = i;
                rows[n2] = rows[n2] | 1 << j;
            }
        }
        int ans = 0;
        for (int mask = 1; mask < 1 << n; ++mask) {
            if (Integer.bitCount(mask) != numSelect) continue;
            int t = 0;
            for (int x : rows) {
                if ((x & mask) != x) continue;
                ++t;
            }
            ans = Math.max(ans, t);
        }
        return ans;
    }
}
