package original;

import java.util.HashMap;
import java.util.List;

class IsSorted {
    /**
     * * Given a list of numbers, return whether or not they are sorted
     * in ascending order. If list has more than 1 duplicate of the same
     * number, return False. Assume no negative numbers and only integers.
     *
     * Examples
     * isSorted([5]) ➞ True
     * isSorted([1, 2, 3, 4, 5]) ➞ True
     * isSorted([1, 3, 2, 4, 5]) ➞ False
     * isSorted([1, 2, 3, 4, 5, 6]) ➞ True
     * isSorted([1, 2, 3, 4, 5, 6, 7]) ➞ True
     * isSorted([1, 3, 2, 4, 5, 6, 7]) ➞ False
     * isSorted([1, 2, 2, 3, 3, 4]) ➞ True
     * isSorted([1, 2, 2, 2, 3, 4]) ➞ False
     *
     * > isSorted([5])
     * true
     * > isSorted([1, 2, 3, 4, 5])
     * true
     * > isSorted([1, 3, 2, 4, 5])
     * false
     * > isSorted([1, 2, 3, 4, 5, 6])
     * true
     * > isSorted([1, 2, 3, 4, 5, 6, 7])
     * true
     * > isSorted([1, 3, 2, 4, 5, 6, 7])
     * false
     * > isSorted([])
     * true
     * > isSorted([1])
     * true
     * > isSorted([3, 2, 1])
     * false
     * > isSorted([1, 2, 2, 2, 3, 4])
     * false
     * > isSorted([1, 2, 3, 3, 3, 4])
     * false
     * > isSorted([1, 2, 2, 3, 3, 4])
     * true
     * > isSorted([1, 2, 3, 4])
     * true
     */
    public static Boolean isSorted(List<Object> lst) {
        if (lst.size() <= 1) {
            return true;
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < lst.size() - 1; i++) {
            int curr = (int) lst.get(i);
            map.put(curr, map.getOrDefault(curr, 0) + 1);
            if (map.get(curr) > 2) {
                return false;
            }
        }

        int prev = (int) lst.get(0);
        for (int i = 1; i < lst.size(); i++) {
            int curr = (int) lst.get(i);
            if (curr < prev) {
                return false;
            }
            prev = curr;
        }
        return true;
    }

}