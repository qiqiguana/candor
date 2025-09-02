package original;

class Solution0517 {
    public int findMinMoves(int[] machines) {
  /**
  You have n super washing machines on a line. Initially, each washing machine has some dresses or is empty. For each move, you could choose any m (1 &lt;= m &lt;= n) washing machines, and pass one dress of each washing machine to one of its adjacent washing machines at the same time. Given an integer array machines representing the number of dresses in each washing machine from left to right on the line, return the minimum number of moves to make all the washing machines have the same number of dresses. If it is not possible to do it, return -1. &nbsp; Example 1: Input: machines = [1,0,5] Output: 3 Explanation: 1st move: 1 0 &lt;-- 5 =&gt; 1 1 4 2nd move: 1 &lt;-- 1 &lt;-- 4 =&gt; 2 1 3 3rd move: 2 1 &lt;-- 3 =&gt; 2 2 2 Example 2: Input: machines = [0,3,0] Output: 2 Explanation: 1st move: 0 &lt;-- 3 0 =&gt; 1 2 0 2nd move: 1 2 --&gt; 0 =&gt; 1 1 1 Example 3: Input: machines = [0,2,0] Output: -1 Explanation: It&#39;s impossible to make all three washing machines have the same number of dresses. &nbsp; Constraints: n == machines.length 1 &lt;= n &lt;= 104 0 &lt;= machines[i] &lt;= 105
  */
        int n = machines.length;
        int s = 0;
        for (int x : machines) {
            s += x;
        }
        if (s % n != 0) {
            return -1;
        }
        int k = s / n;
        s = 0;
        int ans = 0;
        for (int x : machines) {
            x -= k;
            s += x;
            ans = Math.max(ans, Math.max(Math.abs(s), x));
        }
        return ans;
    }
}