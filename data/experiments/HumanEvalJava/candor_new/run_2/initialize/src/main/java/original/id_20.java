package original;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class FindClosestElements {
    /**
     * From a supplied list of numbers (of length at least two) select and return two that are the closest to each
     * other and return them in order (smaller number, larger number).
     *
     * > findClosestElements([1.0, 2.0, 3.9, 4.0, 5.0, 2.2])
     * [3.9, 4.0]
     * > findClosestElements([1.0, 2.0, 5.9, 4.0, 5.0])
     * [5.0, 5.9]
     * > findClosestElements([1.0, 2.0, 3.0, 4.0, 5.0, 2.2])
     * [2.0, 2.2]
     * > findClosestElements([1.0, 2.0, 3.0, 4.0, 5.0, 2.0])
     * [2.0, 2.0]
     * > findClosestElements([1.1, 2.2, 3.1, 4.1, 5.1])
     * [2.2, 3.1]
     */
    public static List<Double> findClosestElements(List<Double> numbers) {
        List<Double> result = new ArrayList<Double>();
        double minDiff = Double.MAX_VALUE;
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size(); j++) {
                double diff = Math.abs(numbers.get(i) - numbers.get(j));
                if (diff < minDiff) {
                    minDiff = diff;
                    result.clear();
                    result.add(numbers.get(i));
                    result.add(numbers.get(j));
                }
            }
        }
        Collections.sort(result);
        return result;
    }

}