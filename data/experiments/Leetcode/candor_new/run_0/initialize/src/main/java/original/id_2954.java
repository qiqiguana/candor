package original;

class Solution2954 {
    private static final int MOD = (int) (1e9 + 7);
    private static final int MX = 100000;
    private static final int[] FAC = new int[MX + 1];

    static {
        FAC[0] = 1;
        for (int i = 1; i <= MX; i++) {
            FAC[i] = (int) ((long) FAC[i - 1] * i % MOD);
        }
    }

    public int numberOfSequence(int n, int[] sick) {
  /**
  You are given an integer n and an array sick sorted in increasing order, representing positions of infected people in a line of n people. At each step, one uninfected person adjacent to an infected person gets infected. This process continues until everyone is infected. An infection sequence is the order in which uninfected people become infected, excluding those initially infected. Return the number of different infection sequences possible, modulo 109+7. &nbsp; Example 1: Input: n = 5, sick = [0,4] Output: 4 Explanation: There is a total of 6 different sequences overall. Valid infection sequences are [1,2,3], [1,3,2], [3,2,1] and [3,1,2]. [2,3,1] and [2,1,3] are not valid infection sequences because the person at index 2 cannot be infected at the first step. Example 2: Input: n = 4, sick = [1] Output: 3 Explanation: There is a total of 6 different sequences overall. Valid infection sequences are [0,2,3], [2,0,3] and [2,3,0]. [3,2,0], [3,0,2], and [0,3,2] are not valid infection sequences because the infection starts at the person at index 1, then the order of infection is 2, then 3, and hence 3 cannot be infected earlier than 2. &nbsp; Constraints: 2 &lt;= n &lt;= 105 1 &lt;= sick.length &lt;= n - 1 0 &lt;= sick[i] &lt;= n - 1 sick is sorted in increasing order.
  */
        int m = sick.length;
        int[] nums = new int[m + 1];
        nums[0] = sick[0];
        nums[m] = n - sick[m - 1] - 1;
        for (int i = 1; i < m; i++) {
            nums[i] = sick[i] - sick[i - 1] - 1;
        }
        int s = 0;
        for (int x : nums) {
            s += x;
        }
        int ans = FAC[s];
        for (int x : nums) {
            if (x > 0) {
                ans = (int) ((long) ans * qpow(FAC[x], MOD - 2) % MOD);
            }
        }
        for (int i = 1; i < nums.length - 1; ++i) {
            if (nums[i] > 1) {
                ans = (int) ((long) ans * qpow(2, nums[i] - 1) % MOD);
            }
        }
        return ans;
    }

    private int qpow(long a, long n) {
        long ans = 1;
        for (; n > 0; n >>= 1) {
            if ((n & 1) == 1) {
                ans = ans * a % MOD;
            }
            a = a * a % MOD;
        }
        return (int) ans;
    }
}