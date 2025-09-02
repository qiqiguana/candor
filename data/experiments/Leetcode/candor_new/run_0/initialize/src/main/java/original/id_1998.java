package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
class Solution1998 {
    private int[] p;

    public boolean gcdSort(int[] nums) {
  /**
  You are given an integer array nums, and you can perform the following operation any number of times on nums: Swap the positions of two elements nums[i] and nums[j] if gcd(nums[i], nums[j]) &gt; 1 where gcd(nums[i], nums[j]) is the greatest common divisor of nums[i] and nums[j]. Return true if it is possible to sort nums in non-decreasing order using the above swap method, or false otherwise. &nbsp; Example 1: Input: nums = [7,21,3] Output: true Explanation: We can sort [7,21,3] by performing the following operations: - Swap 7 and 21 because gcd(7,21) = 7. nums = [21,7,3] - Swap 21 and 3 because gcd(21,3) = 3. nums = [3,7,21] Example 2: Input: nums = [5,2,6,2] Output: false Explanation: It is impossible to sort the array because 5 cannot be swapped with any other element. Example 3: Input: nums = [10,5,9,3,15] Output: true We can sort [10,5,9,3,15] by performing the following operations: - Swap 10 and 15 because gcd(10,15) = 5. nums = [15,5,9,3,10] - Swap 15 and 3 because gcd(15,3) = 3. nums = [3,5,9,15,10] - Swap 10 and 15 because gcd(10,15) = 5. nums = [3,5,9,10,15] &nbsp; Constraints: 1 &lt;= nums.length &lt;= 3 * 104 2 &lt;= nums[i] &lt;= 105
  */
        int n = 100010;
        p = new int[n];
        Map<Integer, List<Integer>> f = new HashMap<>();
        for (int i = 0; i < n; ++i) {
            p[i] = i;
        }
        int mx = 0;
        for (int num : nums) {
            mx = Math.max(mx, num);
        }
        for (int i = 2; i <= mx; ++i) {
            if (f.containsKey(i)) {
                continue;
            }
            for (int j = i; j <= mx; j += i) {
                f.computeIfAbsent(j, k -> new ArrayList<>()).add(i);
            }
        }
        for (int i : nums) {
            for (int j : f.get(i)) {
                p[find(i)] = find(j);
            }
        }
        int[] s = new int[nums.length];
        System.arraycopy(nums, 0, s, 0, nums.length);
        Arrays.sort(s);
        for (int i = 0; i < nums.length; ++i) {
            if (s[i] != nums[i] && find(nums[i]) != find(s[i])) {
                return false;
            }
        }
        return true;
    }

    int find(int x) {
        if (p[x] != x) {
            p[x] = find(p[x]);
        }
        return p[x];
    }
}