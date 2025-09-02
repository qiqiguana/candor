package original;

class Solution0479 {
    public int largestPalindrome(int n) {
  /**
  Given an integer n, return the largest palindromic integer that can be represented as the product of two n-digits integers. Since the answer can be very large, return it modulo 1337. &nbsp; Example 1: Input: n = 2 Output: 987 Explanation: 99 x 91 = 9009, 9009 % 1337 = 987 Example 2: Input: n = 1 Output: 9 &nbsp; Constraints: 1 &lt;= n &lt;= 8
  */
        int mx = (int) Math.pow(10, n) - 1;
        for (int a = mx; a > mx / 10; --a) {
            int b = a;
            long x = a;
            while (b != 0) {
                x = x * 10 + b % 10;
                b /= 10;
            }
            for (long t = mx; t * t >= x; --t) {
                if (x % t == 0) {
                    return (int) (x % 1337);
                }
            }
        }
        return 9;
    }
}