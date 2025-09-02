package original;

import java.util.ArrayList;
import java.util.List;
class Solution1755 {
    public int minAbsDifference(int[] nums, int goal) {
  /**
  You are given an integer array nums and an integer goal. You want to choose a subsequence of nums such that the sum of its elements is the closest possible to goal. That is, if the sum of the subsequence&#39;s elements is sum, then you want to minimize the absolute difference abs(sum - goal). Return the minimum possible value of abs(sum - goal). Note that a subsequence of an array is an array formed by removing some elements (possibly all or none) of the original array. &nbsp; Example 1: Input: nums = [5,-7,3,5], goal = 6 Output: 0 Explanation: Choose the whole array as a subsequence, with a sum of 6. This is equal to the goal, so the absolute difference is 0. Example 2: Input: nums = [7,-9,15,-2], goal = -5 Output: 1 Explanation: Choose the subsequence [7,-9,-2], with a sum of -4. The absolute difference is abs(-4 - (-5)) = abs(1) = 1, which is the minimum. Example 3: Input: nums = [1,2,3], goal = -7 Output: 7 &nbsp; Constraints: 1 &lt;= nums.length &lt;= 40 -107 &lt;= nums[i] &lt;= 107 -109 &lt;= goal &lt;= 109
  */
        int n = nums.length;
        List<Integer> lsum = new ArrayList<>();
        List<Integer> rsum = new ArrayList<>();
        dfs(nums, lsum, 0, n / 2, 0);
        dfs(nums, rsum, n / 2, n, 0);

        rsum.sort(Integer::compareTo);
        int res = Integer.MAX_VALUE;

        for (Integer x : lsum) {
            int target = goal - x;
            int left = 0, right = rsum.size();
            while (left < right) {
                int mid = (left + right) >> 1;
                if (rsum.get(mid) < target) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            if (left < rsum.size()) {
                res = Math.min(res, Math.abs(target - rsum.get(left)));
            }
            if (left > 0) {
                res = Math.min(res, Math.abs(target - rsum.get(left - 1)));
            }
        }

        return res;
    }

    private void dfs(int[] nums, List<Integer> sum, int i, int n, int cur) {
        if (i == n) {
            sum.add(cur);
            return;
        }

        dfs(nums, sum, i + 1, n, cur);
        dfs(nums, sum, i + 1, n, cur + nums[i]);
    }
}