package original;

class Solution1035 {
    public int maxUncrossedLines(int[] nums1, int[] nums2) {
  /**
  You are given two integer arrays nums1 and nums2. We write the integers of nums1 and nums2 (in the order they are given) on two separate horizontal lines. We may draw connecting lines: a straight line connecting two numbers nums1[i] and nums2[j] such that: nums1[i] == nums2[j], and the line we draw does not intersect any other connecting (non-horizontal) line. Note that a connecting line cannot intersect even at the endpoints (i.e., each number can only belong to one connecting line). Return the maximum number of connecting lines we can draw in this way. &nbsp; Example 1: Input: nums1 = [1,4,2], nums2 = [1,2,4] Output: 2 Explanation: We can draw 2 uncrossed lines as in the diagram. We cannot draw 3 uncrossed lines, because the line from nums1[1] = 4 to nums2[2] = 4 will intersect the line from nums1[2]=2 to nums2[1]=2. Example 2: Input: nums1 = [2,5,1,2,5], nums2 = [10,5,2,1,5,2] Output: 3 Example 3: Input: nums1 = [1,3,7,1,7,5], nums2 = [1,9,2,5,1] Output: 2 &nbsp; Constraints: 1 &lt;= nums1.length, nums2.length &lt;= 500 1 &lt;= nums1[i], nums2[j] &lt;= 2000
  */
        int m = nums1.length, n = nums2.length;
        int[][] f = new int[m + 1][n + 1];
        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    f[i][j] = f[i - 1][j - 1] + 1;
                } else {
                    f[i][j] = Math.max(f[i - 1][j], f[i][j - 1]);
                }
            }
        }
        return f[m][n];
    }
}