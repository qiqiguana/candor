package original;


import java.util.List;


class MaxElement {
    /**
     * Return the maximum element in the list.
     *
     * > maxElement([1, 2, 3])
     * 3
     * > maxElement([5, 3, -5, 2, -3, 3, 9, 0, 123, 1, -10])
     * 123
     * > maxElement([1, 2, 3])
     * 3
     * > maxElement([5, 3, -5, 2, -3, 3, 9, 0, 124, 1, -10])
     * 124
     */
    public static int maxElement(List<Integer> l) {
        int max = Integer.MIN_VALUE;
        for (Integer i : l) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }


}