package original;

import java.util.Arrays;
class Solution1040 {
    public int[] numMovesStonesII(int[] stones) {
  /**
  There are some stones in different positions on the X-axis. You are given an integer array stones, the positions of the stones. Call a stone an endpoint stone if it has the smallest or largest position. In one move, you pick up an endpoint stone and move it to an unoccupied position so that it is no longer an endpoint stone. In particular, if the stones are at say, stones = [1,2,5], you cannot move the endpoint stone at position 5, since moving it to any position (such as 0, or 3) will still keep that stone as an endpoint stone. The game ends when you cannot make any more moves (i.e., the stones are in three consecutive positions). Return an integer array answer of length 2 where: answer[0] is the minimum number of moves you can play, and answer[1] is the maximum number of moves you can play. &nbsp; Example 1: Input: stones = [7,4,9] Output: [1,2] Explanation: We can move 4 -&gt; 8 for one move to finish the game. Or, we can move 9 -&gt; 5, 4 -&gt; 6 for two moves to finish the game. Example 2: Input: stones = [6,5,4,3,10] Output: [2,3] Explanation: We can move 3 -&gt; 8 then 10 -&gt; 7 to finish the game. Or, we can move 3 -&gt; 7, 4 -&gt; 8, 5 -&gt; 9 to finish the game. Notice we cannot move 10 -&gt; 2 to finish the game, because that would be an illegal move. &nbsp; Constraints: 3 &lt;= stones.length &lt;= 104 1 &lt;= stones[i] &lt;= 109 All the values of stones are unique.
  */
        Arrays.sort(stones);
        int n = stones.length;
        int mi = n;
        int mx = Math.max(stones[n - 1] - stones[1] + 1, stones[n - 2] - stones[0] + 1) - (n - 1);
        for (int i = 0, j = 0; j < n; ++j) {
            while (stones[j] - stones[i] + 1 > n) {
                ++i;
            }
            if (j - i + 1 == n - 1 && stones[j] - stones[i] == n - 2) {
                mi = Math.min(mi, 2);
            } else {
                mi = Math.min(mi, n - (j - i + 1));
            }
        }
        return new int[] {mi, mx};
    }
}