package original;

import java.util.ArrayList;
import java.util.List;


class Factorize {
    /**
     * Return list of prime factors of given integer in the order from smallest to largest.
     * Each of the factors should be listed number of times corresponding to how many times it appears in factorization.
     * Input number should be equal to the product of all factors
     *
     * > factorize(8)
     * [2, 2, 2]
     * > factorize(25)
     * [5, 5]
     * > factorize(70)
     * [2, 5, 7]
     * > factorize(2)
     * [2]
     * > factorize(4)
     * [2, 2]
     * > factorize(8)
     * [2, 2, 2]
     * > factorize(57)
     * [3, 19]
     * > factorize(3249)
     * [3, 3, 19, 19]
     * > factorize(185193)
     * [3, 3, 3, 19, 19, 19]
     * > factorize(20577)
     * [3, 19, 19, 19]
     * > factorize(18)
     * [2, 3, 3]
     */
    public static List<Integer> factorize(int n) {
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 2; i <= n; i++) {
            while (n % i == 0) {
                result.add(i);
                n /= i;
            }
        }
        return result;
    }


}