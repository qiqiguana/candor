package original;

import java.util.ArrayList;
import java.util.List;


class RemoveDuplicates {
    /**
     * From a list of integers, remove all elements that occur more than once.
     * Keep order of elements left the same as in the input.
     *
     * > removeDuplicates([1, 2, 3, 2, 4])
     * [1, 3, 4]
     * > removeDuplicates([])
     * []
     * > removeDuplicates([1, 2, 3, 4])
     * [1, 2, 3, 4]
     * > removeDuplicates([1, 2, 3, 2, 4, 3, 5])
     * [1, 4, 5]
     */
    public static List<Object> removeDuplicates(List<Object> numbers) {
        List<Object> result = new ArrayList<Object>();
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.indexOf(numbers.get(i)) == numbers.lastIndexOf(numbers.get(i))) {
                result.add(numbers.get(i));
            }
        }
        return result;
    }


}