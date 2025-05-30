package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


class SortArray1 {
    /**
     * * Given an array of non-negative integers, return a copy of the given array after sorting,
     * you will sort the given array in ascending order if the sum( first index value, last index value) is odd,
     * or sort it in descending order if the sum( first index value, last index value) is even.
     *
     * Note:
     * * don't change the given array.
     *
     * Examples:
     * * sort_array([]) => []
     * * sort_array([5]) => [5]
     * * sort_array([2, 4, 3, 0, 1, 5]) => [0, 1, 2, 3, 4, 5]
     * * sort_array([2, 4, 3, 0, 1, 5, 6]) => [6, 5, 4, 3, 2, 1, 0]
     *
     * > sortArray([])
     * []
     * > sortArray([5])
     * [5]
     * > sortArray([2, 4, 3, 0, 1, 5])
     * [0, 1, 2, 3, 4, 5]
     * > sortArray([2, 4, 3, 0, 1, 5, 6])
     * [6, 5, 4, 3, 2, 1, 0]
     * > sortArray([2, 1])
     * [1, 2]
     * > sortArray([15, 42, 87, 32, 11, 0])
     * [0, 11, 15, 32, 42, 87]
     * > sortArray([21, 14, 23, 11])
     * [23, 21, 14, 11]
     */
    public static List<Object> sortArray(List<Object> array) {
        if (array.size() <= 1) {
            return array;
        }
        int sum = (Integer) array.get(0) + (Integer) array.get(array.size() - 1);
        List<Object> sortedArray = new ArrayList<Object>(array);
        Collections.sort(sortedArray, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                return (Integer) o1 - (Integer) o2;
            }
        });
        if (sum % 2 == 0) {
            Collections.reverse(sortedArray);
        }
        return sortedArray;
    }


}