package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class StrangeSortList {
    /**
     * * Given list of integers, return list in strange order.
     * Strange sorting, is when you start with the minimum value,
     * then maximum of the remaining integers, then minimum and so on.
     *
     * Examples:
     * strange_sort_list([1, 2, 3, 4]) == [1, 4, 2, 3]
     * strange_sort_list([5, 5, 5, 5]) == [5, 5, 5, 5]
     * strange_sort_list([]) == []
     *
     * > strangeSortList([1, 2, 3, 4])
     * [1, 4, 2, 3]
     * > strangeSortList([5, 6, 7, 8, 9])
     * [5, 9, 6, 8, 7]
     * > strangeSortList([1, 2, 3, 4, 5])
     * [1, 5, 2, 4, 3]
     * > strangeSortList([5, 6, 7, 8, 9, 1])
     * [1, 9, 5, 8, 6, 7]
     * > strangeSortList([5, 5, 5, 5])
     * [5, 5, 5, 5]
     * > strangeSortList([])
     * []
     * > strangeSortList([1, 2, 3, 4, 5, 6, 7, 8])
     * [1, 8, 2, 7, 3, 6, 4, 5]
     * > strangeSortList([0, 2, 2, 2, 5, 5, -5, -5])
     * [-5, 5, -5, 5, 0, 2, 2, 2]
     * > strangeSortList([111111])
     * [111111]
     */
    public static List<Object> strangeSortList(List<Object> lst) {
        List<Object> result = new ArrayList<Object>();
        List<Integer> sorted = new ArrayList<Integer>();
        for (Object value : lst) {
            if (value instanceof Integer) {
                sorted.add((Integer) value);
            }
        }
        Collections.sort(sorted);
        int i = 0;
        int j = sorted.size() - 1;
        while (i <= j) {
            result.add(sorted.get(i));
            if (i != j) {
                result.add(sorted.get(j));
            }
            i++;
            j--;
        }
        return result;
    }


}