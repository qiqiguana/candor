package original;


import java.util.Arrays;
import java.util.List;


class EvenOddCount {
    /**
     * Given an integer. return a tuple that has the number of even and odd digits respectively.
     *
     * Example:
     * evenOddCount(-12) ==> (1, 1)
     * evenOddCount(123) ==> (1, 2)
     *
     * > evenOddCount(7)
     * [0, 1]
     * > evenOddCount(-78)
     * [1, 1]
     * > evenOddCount(3452)
     * [2, 2]
     * > evenOddCount(346211)
     * [3, 3]
     * > evenOddCount(-345821)
     * [3, 3]
     * > evenOddCount(-2)
     * [1, 0]
     * > evenOddCount(-45347)
     * [2, 3]
     * > evenOddCount(0)
     * [1, 0]
     */
    public static List<Integer> evenOddCount(int num) {
        int even = 0;
        int odd = 0;
        if (num == 0) {
            even++;
        }
        while (num != 0) {
            if (num % 2 == 0) {
                even++;
            } else {
                odd++;
            }
            num /= 10;
        }
        return Arrays.asList(even, odd);
    }


}