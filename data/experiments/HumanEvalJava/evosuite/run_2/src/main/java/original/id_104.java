package original;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class UniqueDigits {
    /**
     * Given a list of positive integers x. return a sorted list of all
     * elements that hasn't any even digit.
     *
     * Note: Returned list should be sorted in increasing order.
     *
     * For example:
     * >>> uniqueDigits([15, 33, 1422, 1])
     * [1, 15, 33]
     * >>> uniqueDigits([152, 323, 1422, 10])
     * []
     *
     * > uniqueDigits([15, 33, 1422, 1])
     * [1, 15, 33]
     * > uniqueDigits([152, 323, 1422, 10])
     * []
     * > uniqueDigits([12345, 2033, 111, 151])
     * [111, 151]
     * > uniqueDigits([135, 103, 31])
     * [31, 135]
     */
    public static List<Object> uniqueDigits(List<Integer> x) {
        List<Object> result = new ArrayList<Object>();
        for (int value : x) {
            boolean hasEven = false;
            int oldValue = value;
            while (value > 0) {
                if (value % 2 == 0) {
                    hasEven = true;
                    break;
                }
                value /= 10;
            }
            if (!hasEven) {
                result.add(oldValue);
            }
        }
        Collections.sort(result, new Comparator<Object>() {
            public int compare(Object a, Object b) {
                return (Integer) a - (Integer) b;
            }
        });
        return result;
    }

}