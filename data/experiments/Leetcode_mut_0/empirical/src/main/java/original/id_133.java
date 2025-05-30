package original;

import java.util.List;


class SumSquares {
    /**
     * You are given a list of numbers.
     * You need to return the sum of squared numbers in the given list,
     * round each element in the list to the upper int(Ceiling) first.
     * Examples:
     * For lst = [1,2,3] the output should be 14
     * For lst = [1,4,9] the output should be 98
     * For lst = [1,3,5,7] the output should be 84
     * For lst = [1.4,4.2,0] the output should be 29
     * For lst = [-2.4,1,1] the output should be 6
     *
     *
     *
     * > sumSquares([1, 2, 3])
     * 14
     * > sumSquares([1.0, 2, 3])
     * 14
     * > sumSquares([1, 3, 5, 7])
     * 84
     * > sumSquares([1.4, 4.2, 0])
     * 29
     * > sumSquares([-2.4, 1, 1])
     * 6
     * > sumSquares([100, 1, 15, 2])
     * 10230
     * > sumSquares([10000, 10000])
     * 200000000
     * > sumSquares([-1.4, 4.6, 6.3])
     * 75
     * > sumSquares([-1.4, 17.9, 18.9, 19.9])
     * 1086
     * > sumSquares([0])
     * 0
     * > sumSquares([-1])
     * 1
     * > sumSquares([-1, 1, 0])
     * 2
     */
    public static int sumSquares(List<Number> lst) {
        int sum = 0;
        for (Number n : lst) {
            sum += Math.pow(Math.ceil(n.doubleValue()), 2);
        }
        return sum;
    }


}