package original;

class Solution3171 {
    public int minimumDifference(int[] nums, int k) {
  /**
  You are given an array nums and an integer k. You need to find a subarray of nums such that the absolute difference between k and the bitwise OR of the subarray elements is as small as possible. In other words, select a subarray nums[l..r] such that |k - (nums[l] OR nums[l + 1] ... OR nums[r])| is minimum. Return the minimum possible value of the absolute difference. A subarray is a contiguous non-empty sequence of elements within an array. &nbsp; Example 1: Input: nums = [1,2,4,5], k = 3 Output: 0 Explanation: The subarray nums[0..1] has OR value 3, which gives the minimum absolute difference |3 - 3| = 0. Example 2: Input: nums = [1,3,1,3], k = 2 Output: 1 Explanation: The subarray nums[1..1] has OR value 3, which gives the minimum absolute difference |3 - 2| = 1. Example 3: Input: nums = [1], k = 10 Output: 9 Explanation: There is a single subarray with OR value 1, which gives the minimum absolute difference |10 - 1| = 9. &nbsp; Constraints: 1 &lt;= nums.length &lt;= 105 1 &lt;= nums[i] &lt;= 109 1 &lt;= k &lt;= 109
  */
        int mx = 0;
        for (int x : nums) {
            mx = Math.max(mx, x);
        }
        int m = 32 - Integer.numberOfLeadingZeros(mx);
        int[] cnt = new int[m];
        int n = nums.length;
        int ans = Integer.MAX_VALUE;
        for (int i = 0, j = 0, s = 0; j < n; ++j) {
            s |= nums[j];
            ans = Math.min(ans, Math.abs(s - k));
            for (int h = 0; h < m; ++h) {
                if ((nums[j] >> h & 1) == 1) {
                    ++cnt[h];
                }
            }
            while (i < j && s > k) {
                for (int h = 0; h < m; ++h) {
                    if ((nums[i] >> h & 1) == 1 && --cnt[h] == 0) {
                        s ^= 1 << h;
                    }
                }
                ++i;
                ans = Math.min(ans, Math.abs(s - k));
            }
        }
        return ans;
    }
}