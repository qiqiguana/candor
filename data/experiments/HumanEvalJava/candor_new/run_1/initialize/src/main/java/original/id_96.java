package original;


import java.util.ArrayList;
import java.util.List;


class CountUpTo {
    /**
     * Implement a function that takes an non-negative integer and returns an array of the first n
     * integers that are prime numbers and less than n.
     * for example:
     * countUpTo(5) => [2,3]
     * countUpTo(11) => [2,3,5,7]
     * countUpTo(0) => []
     * countUpTo(20) => [2,3,5,7,11,13,17,19]
     * countUpTo(1) => []
     * countUpTo(18) => [2,3,5,7,11,13,17]
     *
     * > countUpTo(5)
     * [2, 3]
     * > countUpTo(6)
     * [2, 3, 5]
     * > countUpTo(7)
     * [2, 3, 5]
     * > countUpTo(10)
     * [2, 3, 5, 7]
     * > countUpTo(0)
     * []
     * > countUpTo(22)
     * [2, 3, 5, 7, 11, 13, 17, 19]
     * > countUpTo(1)
     * []
     * > countUpTo(18)
     * [2, 3, 5, 7, 11, 13, 17]
     * > countUpTo(47)
     * [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43]
     * > countUpTo(101)
     * [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97]
     */
    public static List<Object> countUpTo(int n) {
        List<Object> result = new ArrayList<Object>();
        int i = 2;
        while (i < n) {
            if (isPrime(i)) {
                result.add(i);
            }
            i++;
        }
        return result;
    }

    private static boolean isPrime(int n) {
        int i = 2;
        while (i < n) {
            if (n % i == 0) {
                return false;
            }
            i++;
        }
        return true;
    }


}