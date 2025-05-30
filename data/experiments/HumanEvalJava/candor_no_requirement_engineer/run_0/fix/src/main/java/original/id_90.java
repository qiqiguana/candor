package original;


import java.util.List;


class NextSmallest {
    /**
     * * You are given a list of integers.
     * Write a function next_smallest() that returns the 2nd smallest element of the list.
     * Return null if there is no such element.
     * Examples:
     * nextSmallest([1, 2, 3, 4, 5]) == 2
     * nextSmallest([5, 1, 4, 3, 2]) == 2
     * nextSmallest([]) == None
     * nextSmallest([1, 1]) == None
     *
     * > nextSmallest([1, 2, 3, 4, 5])
     * 2
     * > nextSmallest([5, 1, 4, 3, 2])
     * 2
     * > nextSmallest([])
     * null
     * > nextSmallest([1, 1])
     * null
     * > nextSmallest([1, 1, 1, 1, 0])
     * 1
     * > nextSmallest([1, 1])
     * null
     * > nextSmallest([-35, 34, 12, -45])
     * -35
     */
    public static Integer nextSmallest(List<Object> lst) {
        if (lst.size() <= 1) {
            return null;
        }
        Integer smallest = (Integer) lst.get(0);
        Integer nextSmallest = null;
        for (int i = 1; i < lst.size(); i++) {
            Integer current = (Integer) lst.get(i);
            if (current < smallest) {
                nextSmallest = smallest;
                smallest = current;
            } else if (nextSmallest == null || current < nextSmallest) {
                if (current != smallest)
                    nextSmallest = current;
            }
        }
        return nextSmallest;
    }


}