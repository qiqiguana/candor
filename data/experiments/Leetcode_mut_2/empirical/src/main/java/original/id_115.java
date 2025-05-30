package original;


import java.util.List;


class MaxFill {
    /**
     * * You are given a rectangular grid of wells. Each row represents a single well,
     * and each 1 in a row represents a single unit of water.
     * Each well has a corresponding bucket that can be used to extract water from it,
     * and all buckets have the same capacity.
     * Your task is to use the buckets to empty the wells.
     * Output the number of times you need to lower the buckets.
     *
     * Example 1:
     * Input:
     * grid : [[0,0,1,0], [0,1,0,0], [1,1,1,1]]
     * bucket_capacity : 1
     * Output: 6
     *
     * Example 2:
     * Input:
     * grid : [[0,0,1,1], [0,0,0,0], [1,1,1,1], [0,1,1,1]]
     * bucket_capacity : 2
     * Output: 5
     *
     * Example 3:
     * Input:
     * grid : [[0,0,0], [0,0,0]]
     * bucket_capacity : 5
     * Output: 0
     *
     * Constraints:
     * * all wells have the same length
     * * 1 <= grid.length <= 10^2
     * * 1 <= grid[:,1].length <= 10^2
     * * grid[i][j] -> 0 | 1
     * * 1 <= capacity <= 10
     *
     * > maxFill([[0, 0, 1, 0], [0, 1, 0, 0], [1, 1, 1, 1]], 1)
     * 6
     * > maxFill([[0, 0, 1, 1], [0, 0, 0, 0], [1, 1, 1, 1], [0, 1, 1, 1]], 2)
     * 5
     * > maxFill([[0, 0, 0], [0, 0, 0]], 5)
     * 0
     * > maxFill([[1, 1, 1, 1], [1, 1, 1, 1]], 2)
     * 4
     * > maxFill([[1, 1, 1, 1], [1, 1, 1, 1]], 9)
     * 2
     */
    public static int maxFill(List<List<Integer>> grid, int capacity) {
        int sum = 0;
        for (List<Integer> arr : grid) {
            int sumArr = 0;
            for (Integer i : arr) {
                sumArr += i;
            }
            sum += Math.ceil((double) sumArr / capacity);
        }
        return sum;
    }


}