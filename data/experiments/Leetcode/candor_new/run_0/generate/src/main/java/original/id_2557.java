package original;

import java.util.Collections;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
class Solution2557 {
    public int maxCount(int[] banned, int n, long maxSum) {
  /**
  You are given an integer array banned and two integers n and maxSum. You are choosing some number of integers following the below rules: The chosen integers have to be in the range [1, n]. Each integer can be chosen at most once. The chosen integers should not be in the array banned. The sum of the chosen integers should not exceed maxSum. Return the maximum number of integers you can choose following the mentioned rules. &nbsp; Example 1: Input: banned = [1,4,6], n = 6, maxSum = 4 Output: 1 Explanation: You can choose the integer 3. 3 is in the range [1, 6], and do not appear in banned. The sum of the chosen integers is 3, which does not exceed maxSum. Example 2: Input: banned = [4,3,5,6], n = 7, maxSum = 18 Output: 3 Explanation: You can choose the integers 1, 2, and 7. All these integers are in the range [1, 7], all do not appear in banned, and their sum is 10, which does not exceed maxSum. &nbsp; Constraints: 1 &lt;= banned.length &lt;= 105 1 &lt;= banned[i] &lt;= n &lt;= 109 1 &lt;= maxSum &lt;= 1015
  */
        Set<Integer> black = new HashSet<>();
        black.add(0);
        black.add(n + 1);
        for (int x : banned) {
            black.add(x);
        }
        List<Integer> ban = new ArrayList<>(black);
        Collections.sort(ban);
        int ans = 0;
        for (int k = 1; k < ban.size(); ++k) {
            int i = ban.get(k - 1), j = ban.get(k);
            int left = 0, right = j - i - 1;
            while (left < right) {
                int mid = (left + right + 1) >>> 1;
                if ((i + 1 + i + mid) * 1L * mid / 2 <= maxSum) {
                    left = mid;
                } else {
                    right = mid - 1;
                }
            }
            ans += left;
            maxSum -= (i + 1 + i + left) * 1L * left / 2;
            if (maxSum <= 0) {
                break;
            }
        }
        return ans;
    }
}