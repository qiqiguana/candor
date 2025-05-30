package original;

import java.util.ArrayList;
import java.util.List;


class EvenOddPalindrome {
    /**
     * * Given a positive integer n, return a tuple that has the number of even and odd
     * integer palindromes that fall within the range(1, n), inclusive.
     *
     * Example 1:
     *
     * Input: 3
     * Output: (1, 2)
     * Explanation:
     * Integer palindrome are 1, 2, 3. one of them is even, and two of them are odd.
     *
     * Example 2:
     *
     * Input: 12
     * Output: (4, 6)
     * Explanation:
     * Integer palindrome are 1, 2, 3, 4, 5, 6, 7, 8, 9, 11. four of them are even, and 6 of them are odd.
     *
     * Note:
     * 1. 1 <= n <= 10^3
     * 2. returned tuple has the number of even and odd integer palindromes respectively.
     *
     * > evenOddPalindrome(123)
     * [8, 13]
     * > evenOddPalindrome(12)
     * [4, 6]
     * > evenOddPalindrome(3)
     * [1, 2]
     * > evenOddPalindrome(63)
     * [6, 8]
     * > evenOddPalindrome(25)
     * [5, 6]
     * > evenOddPalindrome(19)
     * [4, 6]
     * > evenOddPalindrome(9)
     * [4, 5]
     * > evenOddPalindrome(1)
     * [0, 1]
     */
    public static List<Integer> evenOddPalindrome(int n) {
        List<Integer> result = new ArrayList<Integer>();
        int even = 0;
        int odd = 0;
        for (int i = 1; i <= n; i++) {
            if (isPalindrome(i)) {
                if (i % 2 == 0) {
                    even++;
                } else {
                    odd++;
                }
            }
        }
        result.add(even);
        result.add(odd);
        return result;
    }

    private static boolean isPalindrome(int n) {
        int m = n;
        int reversed = 0;
        while (m > 0) {
            reversed = reversed * 10 + m % 10;
            m /= 10;
        }
        return n == reversed;
    }


}