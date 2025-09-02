package original;

class Solution1359 {
    public int countOrders(int n) {
  /**
  Given n orders, each order consists of a pickup and a delivery service. Count all valid pickup/delivery possible sequences such that delivery(i) is always after of&nbsp;pickup(i).&nbsp; Since the answer&nbsp;may be too large,&nbsp;return it modulo&nbsp;10^9 + 7. &nbsp; Example 1: Input: n = 1 Output: 1 Explanation: Unique order (P1, D1), Delivery 1 always is after of Pickup 1. Example 2: Input: n = 2 Output: 6 Explanation: All possible orders: (P1,P2,D1,D2), (P1,P2,D2,D1), (P1,D1,P2,D2), (P2,P1,D1,D2), (P2,P1,D2,D1) and (P2,D2,P1,D1). This is an invalid order (P1,D2,P2,D1) because Pickup 2 is after of Delivery 2. Example 3: Input: n = 3 Output: 90 &nbsp; Constraints: 1 &lt;= n &lt;= 500
  */
        final int mod = (int) 1e9 + 7;
        long f = 1;
        for (int i = 2; i <= n; ++i) {
            f = f * i * (2 * i - 1) % mod;
        }
        return (int) f;
    }
}