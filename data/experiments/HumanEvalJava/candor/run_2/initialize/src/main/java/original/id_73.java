package original;


import java.util.List;


class SmallestChange {
    /**
     * * Given an array arr of integers, find the minimum number of elements that
     * need to be changed to make the array palindromic. A palindromic array is an array that
     * is read the same backwards and forwards. In one change, you can change one element to any other element.
     *
     * For example:
     * smallest_change([1,2,3,5,4,7,9,6]) == 4
     * smallest_change([1, 2, 3, 4, 3, 2, 2]) == 1
     * smallest_change([1, 2, 3, 2, 1]) == 0
     *
     * > smallestChange([1, 2, 3, 5, 4, 7, 9, 6])
     * 4
     * > smallestChange([1, 2, 3, 4, 3, 2, 2])
     * 1
     * > smallestChange([1, 4, 2])
     * 1
     * > smallestChange([1, 4, 4, 2])
     * 1
     * > smallestChange([1, 2, 3, 2, 1])
     * 0
     * > smallestChange([3, 1, 1, 3])
     * 0
     * > smallestChange([1])
     * 0
     * > smallestChange([0, 1])
     * 1
     */
    public static int smallestChange(List<Integer> arr) {
        int result = 0;
        int i = 0;
        int j = arr.size() - 1;
        while (i < j) {
            if (arr.get(i) != arr.get(j)) {
                result++;
            }
            i++;
            j--;
        }
        return result;
    }


}