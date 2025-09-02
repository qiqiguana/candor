package original;

import java.util.HashMap;
import java.util.Map;
class Solution3154 {
    private Map<Long, Integer> f = new HashMap<>();
    private int k;

    public int waysToReachStair(int k) {
  /**
  You are given a non-negative integer k. There exists a staircase with an infinite number of stairs, with the lowest stair numbered 0. Alice has an integer jump, with an initial value of 0. She starts on stair 1 and wants to reach stair k using any number of operations. If she is on stair i, in one operation she can: Go down to stair i - 1. This operation cannot be used consecutively or on stair 0. Go up to stair i + 2jump. And then, jump becomes jump + 1. Return the total number of ways Alice can reach stair k. Note that it is possible that Alice reaches the stair k, and performs some operations to reach the stair k again. &nbsp; Example 1: Input: k = 0 Output: 2 Explanation: The 2 possible ways of reaching stair 0 are: Alice starts at stair 1. Using an operation of the first type, she goes down 1 stair to reach stair 0. Alice starts at stair 1. Using an operation of the first type, she goes down 1 stair to reach stair 0. Using an operation of the second type, she goes up 20 stairs to reach stair 1. Using an operation of the first type, she goes down 1 stair to reach stair 0. Example 2: Input: k = 1 Output: 4 Explanation: The 4 possible ways of reaching stair 1 are: Alice starts at stair 1. Alice is at stair 1. Alice starts at stair 1. Using an operation of the first type, she goes down 1 stair to reach stair 0. Using an operation of the second type, she goes up 20 stairs to reach stair 1. Alice starts at stair 1. Using an operation of the second type, she goes up 20 stairs to reach stair 2. Using an operation of the first type, she goes down 1 stair to reach stair 1. Alice starts at stair 1. Using an operation of the first type, she goes down 1 stair to reach stair 0. Using an operation of the second type, she goes up 20 stairs to reach stair 1. Using an operation of the first type, she goes down 1 stair to reach stair 0. Using an operation of the second type, she goes up 21 stairs to reach stair 2. Using an operation of the first type, she goes down 1 stair to reach stair 1. &nbsp; Constraints: 0 &lt;= k &lt;= 109
  */
        this.k = k;
        return dfs(1, 0, 0);
    }

    private int dfs(int i, int j, int jump) {
        if (i > k + 1) {
            return 0;
        }
        long key = ((long) i << 32) | jump << 1 | j;
        if (f.containsKey(key)) {
            return f.get(key);
        }
        int ans = i == k ? 1 : 0;
        if (i > 0 && j == 0) {
            ans += dfs(i - 1, 1, jump);
        }
        ans += dfs(i + (1 << jump), 0, jump + 1);
        f.put(key, ans);
        return ans;
    }
}