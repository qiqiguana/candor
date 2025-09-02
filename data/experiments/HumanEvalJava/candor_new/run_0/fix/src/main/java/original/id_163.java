package original;


import java.util.ArrayList;
import java.util.List;


class GenerateIntegers {
    /**
     * Given two positive integers a and b, return the even digits between a
     * and b, in ascending order.
     *
     * For example:
     * generateIntegers(2, 8) => [2, 4, 6, 8]
     * generateIntegers(8, 2) => [2, 4, 6, 8]
     * generateIntegers(10, 14) => []
     *
     * > generateIntegers(2, 10)
     * [2, 4, 6, 8]
     * > generateIntegers(10, 2)
     * [2, 4, 6, 8]
     * > generateIntegers(132, 2)
     * [2, 4, 6, 8]
     * > generateIntegers(17, 89)
     * []
     */
    public static List<Object> generateIntegers(int a, int b) {

        int lower = Math.max(2, Math.min(a, b));
        int upper = Math.min(8, Math.max(a, b));
        List<Object> result = new ArrayList<>();
        for (int i = lower; i <= upper; i++) {
            if (i % 2 == 0) {
                result.add(i);
            }
        }
        return result;
    }


}