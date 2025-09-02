package original;

class Solution2907 {
    public int maxProfit(int[] prices, int[] profits) {
  /**
  Given the 0-indexed arrays prices and profits of length n. There are n items in an store where the ith item has a price of prices[i] and a profit of profits[i]. We have to pick three items with the following condition: prices[i] &lt; prices[j] &lt; prices[k] where i &lt; j &lt; k. If we pick items with indices i, j and k satisfying the above condition, the profit would be profits[i] + profits[j] + profits[k]. Return the maximum profit we can get, and -1 if it&#39;s not possible to pick three items with the given condition. &nbsp; Example 1: Input: prices = [10,2,3,4], profits = [100,2,7,10] Output: 19 Explanation: We can&#39;t pick the item with index i=0 since there are no indices j and k such that the condition holds. So the only triplet we can pick, are the items with indices 1, 2 and 3 and it&#39;s a valid pick since prices[1] &lt; prices[2] &lt; prices[3]. The answer would be sum of their profits which is 2 + 7 + 10 = 19. Example 2: Input: prices = [1,2,3,4,5], profits = [1,5,3,4,6] Output: 15 Explanation: We can select any triplet of items since for each triplet of indices i, j and k such that i &lt; j &lt; k, the condition holds. Therefore the maximum profit we can get would be the 3 most profitable items which are indices 1, 3 and 4. The answer would be sum of their profits which is 5 + 4 + 6 = 15. Example 3: Input: prices = [4,3,2,1], profits = [33,20,19,87] Output: -1 Explanation: We can&#39;t select any triplet of indices such that the condition holds, so we return -1. &nbsp; Constraints: 3 &lt;= prices.length == profits.length &lt;= 2000 1 &lt;= prices[i] &lt;= 106 1 &lt;= profits[i] &lt;= 106
  */
        int n = prices.length;
        int ans = -1;
        for (int j = 0; j < n; ++j) {
            int left = 0, right = 0;
            for (int i = 0; i < j; ++i) {
                if (prices[i] < prices[j]) {
                    left = Math.max(left, profits[i]);
                }
            }
            for (int k = j + 1; k < n; ++k) {
                if (prices[j] < prices[k]) {
                    right = Math.max(right, profits[k]);
                }
            }
            if (left > 0 && right > 0) {
                ans = Math.max(ans, left + profits[j] + right);
            }
        }
        return ans;
    }
}