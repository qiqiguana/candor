package original;

import java.util.List;


class Solution {
    /**
     * Given a non-empty list of integers, return the sum of all of the odd elements that are in even positions.
     *
     *
     * Examples
     * solution([5, 8, 7, 1]) ==> 12
     * solution([3, 3, 3, 3, 3]) ==> 9
     * solution([30, 13, 24, 321]) ==>0
     *
     * > solution([3, 3, 3, 3, 3])
     * 9
     * > solution([30, 13, 24, 321])
     * 0
     * > solution([5, 9])
     * 5
     * > solution([2, 4, 8])
     * 0
     * > solution([30, 13, 23, 32])
     * 23
     * > solution([3, 13, 2, 9])
     * 3
     */
    public static int solution(List<Integer> lst) {
        int sum = 0;
        for (int i = 0; i < lst.size(); i++) {
            if (i % 2 == 0 && lst.get(i) % 2 == 1) {
                sum += lst.get(i);
            }
        }
        return sum;
    }


}