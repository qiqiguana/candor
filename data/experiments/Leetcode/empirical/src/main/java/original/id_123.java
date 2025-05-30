package original;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class GetOddCollatz {
    /**
     * * Given a positive integer n, return a sorted list that has the odd numbers in collatz sequence.
     *
     * The Collatz conjecture is a conjecture in mathematics that concerns a sequence defined
     * as follows: start with any positive integer n. Then each term is obtained from the
     * previous term as follows: if the previous term is even, the next term is one half of
     * the previous term. If the previous term is odd, the next term is 3 times the previous
     * term plus 1. The conjecture is that no matter what value of n, the sequence will always reach 1.
     *
     * Note:
     * 1. Collatz(1) is [1].
     * 2. returned list sorted in increasing order.
     *
     * For example:
     * getOddCollatz(5) returns [1, 5] # The collatz sequence for 5 is [5, 16, 8, 4, 2, 1], so the odd numbers are only 1, and 5.
     *
     * > getOddCollatz(14)
     * [1, 5, 7, 11, 13, 17]
     * > getOddCollatz(5)
     * [1, 5]
     * > getOddCollatz(12)
     * [1, 3, 5]
     * > getOddCollatz(1)
     * [1]
     */
    public static List<Integer> getOddCollatz(int n) {
        List<Integer> result = new ArrayList<Integer>();
        while (n != 1) {
            if (n % 2 == 1) {
                result.add(n);
            }
            if (n % 2 == 0) {
                n = n / 2;
            } else {
                n = 3 * n + 1;
            }
        }
        result.add(1);
        Collections.sort(result);
        return result;
    }


}