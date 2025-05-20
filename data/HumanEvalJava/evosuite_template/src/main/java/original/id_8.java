package original;

import java.util.ArrayList;
import java.util.List;


class SumProduct {
    /**
     * For a given list of integers, return a tuple consisting of a sum and a product of all the integers in a list.
     * Empty sum should be equal to 0 and empty product should be equal to 1.
     *
     * > sumProduct([1, 2, 3, 4])
     * [10, 24]
     * > sumProduct([])
     * [0, 1]
     * > sumProduct([1, 1, 1])
     * [3, 1]
     * > sumProduct([100, 0])
     * [100, 0]
     * > sumProduct([3, 5, 7])
     * [15, 105]
     * > sumProduct([10])
     * [10, 10]
     */
    public static List<Integer> sumProduct(List<Object> numbers) {
        Integer sum = 0;
        Integer product = 1;
        for (Object number : numbers) {
            if (number instanceof Integer) {
                sum += (Integer) number;
                product *= (Integer) number;
            } else {
                throw new IllegalArgumentException("Invalid number type");
            }
        }
        List<Integer> result = new ArrayList<Integer>();
        result.add(sum);
        result.add(product);
        return result;
    }


}