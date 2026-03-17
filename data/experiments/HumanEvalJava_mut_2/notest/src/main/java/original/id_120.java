package original;

import java.util.*;


class Maximum1 {
    /**
     * * Given an array arr of integers and a positive integer k, return a sorted list
     * of length k with the maximum k numbers in arr.
     *
     * Example 1:
     *
     * Input: arr = [-3, -4, 5], k = 3
     * Output: [-4, -3, 5]
     *
     * Example 2:
     *
     * Input: arr = [4, -4, 4], k = 2
     * Output: [4, 4]
     *
     * Example 3:
     *
     * Input: arr = [-3, 2, 1, 2, -1, -2, 1], k = 1
     * Output: [2]
     *
     * Note:
     * 1. The length of the array will be in the range of [1, 1000].
     * 2. The elements in the array will be in the range of [-1000, 1000].
     * 3. 0 <= k <= len(arr)
     *
     * > maximum([-3, -4, 5], 3)
     * [-4, -3, 5]
     * > maximum([4, -4, 4], 2)
     * [4, 4]
     * > maximum([-3, 2, 1, 2, -1, -2, 1], 1)
     * [2]
     * > maximum([123, -123, 20, 0, 1, 2, -3], 3)
     * [2, 20, 123]
     * > maximum([-123, 20, 0, 1, 2, -3], 4)
     * [0, 1, 2, 20]
     * > maximum([5, 15, 0, 3, -13, -8, 0], 7)
     * [-13, -8, 0, 0, 3, 5, 15]
     * > maximum([-1, 0, 2, 5, 3, -10], 2)
     * [3, 5]
     * > maximum([1, 0, 5, -7], 1)
     * [5]
     * > maximum([4, -4], 2)
     * [-4, 4]
     * > maximum([-10, 10], 2)
     * [-10, 10]
     * > maximum([1, 2, 3, -23, 243, -400, 0], 0)
     * []
     */
    public static List<Object> maximum(List<Integer> arr, int k) {
        int n = arr.size();
        if (n == 0 || k == 0) {
            return new ArrayList<Object>();
        }
        if (n <= k) {

            Collections.sort(arr);
            return new ArrayList<Object>(arr);
        }
        List<Object> res = new ArrayList<Object>();
        PriorityQueue<Integer> heap = new PriorityQueue<Integer>(k);
        for (int i = 0; i < n; i++) {
            if (i < k) {
                heap.add(arr.get(i));
            } else {
                if (arr.get(i) > heap.peek()) {
                    heap.poll();
                    heap.add(arr.get(i));
                }
            }
        }
        for (int i = 0; i < k; i++) {
            res.add(heap.poll());
        }
        Collections.sort(res, new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                return (int) o1 - (int) o2;
            }
        });
        return res;
    }


}