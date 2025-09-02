package original;


import java.util.List;


class ProdSigns {
    /**
     * * You are given an array arr of integers and you need to return
     * sum of magnitudes of integers multiplied by product of all signs
     * of each number in the array, represented by 1, -1 or 0.
     * Note: return null for empty arr.
     *
     * Example:
     * >>> prodSigns([1, 2, 2, -4]) == -9
     * >>> prodSigns([0, 1]) == 0
     * >>> prodSigns([]) == None
     *
     * > prodSigns([1, 2, 2, -4])
     * -9
     * > prodSigns([0, 1])
     * 0
     * > prodSigns([1, 1, 1, 2, 3, -1, 1])
     * -10
     * > prodSigns([])
     * null
     * > prodSigns([2, 4, 1, 2, -1, -1, 9])
     * 20
     * > prodSigns([-1, 1, -1, 1])
     * 4
     * > prodSigns([-1, 1, 1, 1])
     * -4
     * > prodSigns([-1, 1, 1, 0])
     * 0
     */
    public static Integer prodSigns(List<Object> arr) {
        if (arr.isEmpty()) {
            return null;
        }
        int prod = 1;
        int sum = 0;
        for (Object value : arr) {
            if (value instanceof Integer) {
                int num = (Integer) value;
                if (num > 0) {
                    prod *= 1;
                } else if (num < 0) {
                    prod *= -1;
                } else {
                    prod *= 0;
                }
                sum += Math.abs(num);
            }
        }
        return sum * prod;
    }


}