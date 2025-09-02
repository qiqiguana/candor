package original;

import java.util.Arrays;
class Solution0475 {
    public int findRadius(int[] houses, int[] heaters) {
  /**
  Winter is coming! During the contest, your first job is to design a standard heater with a fixed warm radius to warm all the houses. Every house can be warmed, as long as the house is within the heater&#39;s warm radius range.&nbsp; Given the positions of houses and heaters on a horizontal line, return the minimum radius standard of heaters&nbsp;so that those heaters could cover all houses. Notice that&nbsp;all the heaters follow your radius standard, and the warm radius will the same. &nbsp; Example 1: Input: houses = [1,2,3], heaters = [2] Output: 1 Explanation: The only heater was placed in the position 2, and if we use the radius 1 standard, then all the houses can be warmed. Example 2: Input: houses = [1,2,3,4], heaters = [1,4] Output: 1 Explanation: The two heaters were placed at positions 1 and 4. We need to use a radius 1 standard, then all the houses can be warmed. Example 3: Input: houses = [1,5], heaters = [2] Output: 3 &nbsp; Constraints: 1 &lt;= houses.length, heaters.length &lt;= 3 * 104 1 &lt;= houses[i], heaters[i] &lt;= 109
  */
        Arrays.sort(heaters);
        int res = 0;
        for (int x : houses) {
            int i = Arrays.binarySearch(heaters, x);
            if (i < 0) {
                i = ~i;
            }
            int dis1 = i > 0 ? x - heaters[i - 1] : Integer.MAX_VALUE;
            int dis2 = i < heaters.length ? heaters[i] - x : Integer.MAX_VALUE;
            res = Math.max(res, Math.min(dis1, dis2));
        }
        return res;
    }
}