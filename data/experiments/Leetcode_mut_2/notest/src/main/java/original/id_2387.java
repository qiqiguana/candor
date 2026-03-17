/*
 * Decompiled with CFR 0.152.
 */
package original;

class Solution2387 {
    private int[][] grid;

    Solution2387() {
    }

    public int matrixMedian(int[][] grid) {
        this.grid = grid;
        int m = grid.length;
        int n = grid[0].length;
        int target = m * n + 1 >> 1;
        int left = 0;
        int right = 1000010;
        while (left < right) {
            int mid = left + right >> 1;
            if (this.count(mid) >= target) {
                right = mid;
                continue;
            }
            left = mid + 1;
        }
        return left;
    }

    private int count(int x) {
        int cnt = 0;
        for (int[] row : this.grid) {
            int left = 0;
            int right = row.length;
            while (left < right) {
                int mid = left + right >> 1;
                if (row[mid] >= x) {
                    right = mid;
                    continue;
                }
                left = mid + 1;
            }
            cnt += left;
        }
        return cnt;
    }
}
