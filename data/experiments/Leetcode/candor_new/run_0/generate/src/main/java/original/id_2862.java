package original;

import java.util.List;
class Solution2862 {
    public long maximumSum(List<Integer> nums) {
  /**
  You are given a 1-indexed array nums. Your task is to select a complete subset from nums where every pair of selected indices multiplied is a perfect square,. i. e. if you select ai and aj, i * j must be a perfect square. Return the sum of the complete subset with the maximum sum. &nbsp; Example 1: Input: nums = [8,7,3,5,7,2,4,9] Output: 16 Explanation: We select elements at indices 2 and 8 and 2 * 8 is a perfect square. Example 2: Input: nums = [8,10,3,8,1,13,7,9,4] Output: 20 Explanation: We select elements at indices 1, 4, and 9. 1 * 4, 1 * 9, 4 * 9 are perfect squares. &nbsp; Constraints: 1 &lt;= n == nums.length &lt;= 104 1 &lt;= nums[i] &lt;= 109
  */
        long ans = 0;
        int n = nums.size();
        boolean[] used = new boolean[n + 1];
        int bound = (int) Math.floor(Math.sqrt(n));
        int[] squares = new int[bound + 1];
        for (int i = 1; i <= bound + 1; i++) {
            squares[i - 1] = i * i;
        }
        for (int i = 1; i <= n; i++) {
            long res = 0;
            int idx = 0;
            int curr = i * squares[idx];
            while (curr <= n) {
                res += nums.get(curr - 1);
                curr = i * squares[++idx];
            }
            ans = Math.max(ans, res);
        }
        return ans;
    }
}