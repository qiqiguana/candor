package original;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
class Solution3209 {
    public long countSubarrays(int[] nums, int k) {
  /**
  Given an array of integers nums and an integer k, return the number of subarrays of nums where the bitwise AND of the elements of the subarray equals k. &nbsp; Example 1: Input: nums = [1,1,1], k = 1 Output: 6 Explanation: All subarrays contain only 1&#39;s. Example 2: Input: nums = [1,1,2], k = 1 Output: 3 Explanation: Subarrays having an AND value of 1 are: [1,1,2], [1,1,2], [1,1,2]. Example 3: Input: nums = [1,2,3], k = 2 Output: 2 Explanation: Subarrays having an AND value of 2 are: [1,2,3], [1,2,3]. &nbsp; Constraints: 1 &lt;= nums.length &lt;= 105 0 &lt;= nums[i], k &lt;= 109
  */
        long ans = 0;
        Map<Integer, Integer> pre = new HashMap<>();
        for (int x : nums) {
            Map<Integer, Integer> cur = new HashMap<>();
            for (var e : pre.entrySet()) {
                int y = e.getKey(), v = e.getValue();
                cur.merge(x & y, v, Integer::sum);
            }
            cur.merge(x, 1, Integer::sum);
            ans += cur.getOrDefault(k, 0);
            pre = cur;
        }
        return ans;
    }
}