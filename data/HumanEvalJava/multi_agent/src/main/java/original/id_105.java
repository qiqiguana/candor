package original;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class ByLength {
    /**
     * * Given an array of integers, sort the integers that are between 1 and 9 inclusive,
     * reverse the resulting array, and then replace each digit by its corresponding name from
     * "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine".
     *
     * For example:
     * arr = [2, 1, 1, 4, 5, 8, 2, 3]
     * -> sort arr -> [1, 1, 2, 2, 3, 4, 5, 8]
     * -> reverse arr -> [8, 5, 4, 3, 2, 2, 1, 1]
     * return ["Eight", "Five", "Four", "Three", "Two", "Two", "One", "One"]
     *
     * If the array is empty, return an empty array:
     * arr = []
     * return []
     *
     * If the array has any strange number ignore it:
     * arr = [1, -1 , 55]
     * -> sort arr -> [-1, 1, 55]
     * -> reverse arr -> [55, 1, -1]
     * return = ['One']
     *
     * > byLength([2, 1, 1, 4, 5, 8, 2, 3])
     * ["Eight", "Five", "Four", "Three", "Two", "Two", "One", "One"]
     * > byLength([])
     * []
     * > byLength([1, -1, 55])
     * ["One"]
     * > byLength([1, -1, 3, 2])
     * ["Three", "Two", "One"]
     * > byLength([9, 4, 8])
     * ["Nine", "Eight", "Four"]
     */
    public static List<Object> byLength(List<Object> arr) {
        List<Integer> sorted = new ArrayList<Integer>();
        for (Object value : arr) {
            if (value instanceof Integer) {
                sorted.add((Integer) value);
            }
        }
        Collections.sort(sorted);
        Collections.reverse(sorted);
        List<Object> result = new ArrayList<Object>();
        for (Integer value : sorted) {
            if (value >= 1 && value <= 9) {
                switch (value) {
                    case 1:
                        result.add("One");
                        break;
                    case 2:
                        result.add("Two");
                        break;
                    case 3:
                        result.add("Three");
                        break;
                    case 4:
                        result.add("Four");
                        break;
                    case 5:
                        result.add("Five");
                        break;
                    case 6:
                        result.add("Six");
                        break;
                    case 7:
                        result.add("Seven");
                        break;
                    case 8:
                        result.add("Eight");
                        break;
                    case 9:
                        result.add("Nine");
                        break;
                }
            }
        }
        return result;
    }

}