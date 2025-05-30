package original;

import java.util.ArrayList;
import java.util.List;


class RollingMax {
    /**
     * From a given list of integers, generate a list of rolling maximum element found until given moment
     * in the sequence.
     *
     * > rollingMax([1, 2, 3, 2, 3, 4, 2])
     * [1, 2, 3, 3, 3, 4, 4]
     * > rollingMax([])
     * []
     * > rollingMax([1, 2, 3, 4])
     * [1, 2, 3, 4]
     * > rollingMax([4, 3, 2, 1])
     * [4, 4, 4, 4]
     * > rollingMax([3, 2, 3, 100, 3])
     * [3, 3, 3, 100, 100]
     */
    public static List<Object> rollingMax(List<Object> numbers) {
        List<Object> result = new ArrayList<Object>();
        Integer max = null;
        for (Object number : numbers) {
            if (number instanceof Integer) {
                if (max == null || (Integer) number > max) {
                    max = (Integer) number;
                }
                result.add(max);
            } else {
                throw new IllegalArgumentException("Invalid number type");
            }
        }
        return result;
    }


}