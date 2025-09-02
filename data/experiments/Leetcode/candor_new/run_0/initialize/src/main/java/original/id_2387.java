package original;

class Solution2387 {
    private int[][] grid;

    public int matrixMedian(int[][] grid) {
  /**
  Given an m x n matrix grid containing an odd number of integers where each row is sorted in non-decreasing order, return the median of the matrix. You must solve the problem in less than O(m * n) time complexity. &nbsp; Example 1: Input: grid = [[1,1,2],[2,3,3],[1,3,4]] Output: 2 Explanation: The elements of the matrix in sorted order are 1,1,1,2,2,3,3,3,4. The median is 2. Example 2: Input: grid = [[1,1,3,3,4]] Output: 3 Explanation: The elements of the matrix in sorted order are 1,1,3,3,4. The median is 3. &nbsp; Constraints: m == grid.length n == grid[i].length 1 &lt;= m, n &lt;= 500 m and n are both odd. 1 &lt;= grid[i][j] &lt;= 106 grid[i] is sorted in non-decreasing order.
  */
        this.grid = grid;
        int m = grid.length, n = grid[0].length;
        int target = (m * n + 1) >> 1;
        int left = 0, right = 1000010;
        while (left < right) {
            int mid = (left + right) >> 1;
            if (count(mid) >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private int count(int x) {
        int cnt = 0;
        for (var row : grid) {
            int left = 0, right = row.length;
            while (left < right) {
                int mid = (left + right) >> 1;
                if (row[mid] > x) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            cnt += left;
        }
        return cnt;
    }
}