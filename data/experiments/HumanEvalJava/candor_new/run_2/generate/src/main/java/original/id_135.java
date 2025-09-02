package original;


import java.util.List;


class CanArrange {
    /**
     * Create a function which returns the largest index of an element which
     * is not greater than or equal to the element immediately preceding it. If
     * no such element exists then return -1. The given array will not contain
     * duplicate values.
     *
     * Examples:
     * canArrange([1,2,4,3,5]) = 3
     * canArrange([1,2,3]) = -1
     *
     * > canArrange([1, 2, 4, 3, 5])
     * 3
     * > canArrange([1, 2, 4, 5])
     * -1
     * > canArrange([1, 4, 2, 5, 6, 7, 8, 9, 10])
     * 2
     * > canArrange([4, 8, 5, 7, 3])
     * 4
     * > canArrange([])
     * -1
     */
    public static int canArrange(List<Object> arr) {
        int max = -1;
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) instanceof Integer) {
                if (i > 0 && (int) arr.get(i) < (int) arr.get(i - 1)) {
                    max = i;
                }
            }
        }
        return max;
    }


}