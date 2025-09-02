package original;

class Solution2470 {
    public int subarrayLCM(int[] nums, int k) {
  /**
  Given an integer array nums and an integer k, return the number of subarrays of nums where the least common multiple of the subarray&#39;s elements is k. A subarray is a contiguous non-empty sequence of elements within an array. The least common multiple of an array is the smallest positive integer that is divisible by all the array elements. &nbsp; Example 1: Input: nums = [3,6,2,7,1], k = 6 Output: 4 Explanation: The subarrays of nums where 6 is the least common multiple of all the subarray&#39;s elements are: - [3,6,2,7,1] - [3,6,2,7,1] - [3,6,2,7,1] - [3,6,2,7,1] Example 2: Input: nums = [3], k = 2 Output: 0 Explanation: There are no subarrays of nums where 2 is the least common multiple of all the subarray&#39;s elements. &nbsp; Constraints: 1 &lt;= nums.length &lt;= 1000 1 &lt;= nums[i], k &lt;= 1000
  */
        int n = nums.length;
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            int a = nums[i];
            for (int j = i; j < n; ++j) {
                int b = nums[j];
                int x = lcm(a, b);
                if (x == k) {
                    ++ans;
                }
                a = x;
            }
        }
        return ans;
    }

    private int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}