package original;

class Solution2457 {
    public long makeIntegerBeautiful(long n, int target) {
  /**
  You are given two positive integers n and target. An integer is considered beautiful if the sum of its digits is less than or equal to target. Return the minimum non-negative integer x such that n + x is beautiful. The input will be generated such that it is always possible to make n beautiful. &nbsp; Example 1: Input: n = 16, target = 6 Output: 4 Explanation: Initially n is 16 and its digit sum is 1 + 6 = 7. After adding 4, n becomes 20 and digit sum becomes 2 + 0 = 2. It can be shown that we can not make n beautiful with adding non-negative integer less than 4. Example 2: Input: n = 467, target = 6 Output: 33 Explanation: Initially n is 467 and its digit sum is 4 + 6 + 7 = 17. After adding 33, n becomes 500 and digit sum becomes 5 + 0 + 0 = 5. It can be shown that we can not make n beautiful with adding non-negative integer less than 33. Example 3: Input: n = 1, target = 1 Output: 0 Explanation: Initially n is 1 and its digit sum is 1, which is already smaller than or equal to target. &nbsp; Constraints: 1 &lt;= n &lt;= 1012 1 &lt;= target &lt;= 150 The input will be generated such that it is always possible to make n beautiful.
  */
        long x = 0;
        while (f(n + x) > target) {
            long y = n + x;
            long p = 10;
            while (y % 10 == 0) {
                y /= 10;
                p *= 10;
            }
            x = (y / 10 + 1) * p - n;
        }
        return x;
    }

    private int f(long x) {
        int y = 0;
        while (x > 0) {
            y += x % 10;
            x /= 10;
        }
        return y;
    }
}