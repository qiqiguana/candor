package original;

class Solution0670 {
    public int maximumSwap(int num) {
  /**
  You are given an integer num. You can swap two digits at most once to get the maximum valued number. Return the maximum valued number you can get. &nbsp; Example 1: Input: num = 2736 Output: 7236 Explanation: Swap the number 2 and the number 7. Example 2: Input: num = 9973 Output: 9973 Explanation: No swap. &nbsp; Constraints: 0 &lt;= num &lt;= 108
  */
        char[] s = String.valueOf(num).toCharArray();
        int n = s.length;
        int[] d = new int[n];
        for (int i = 0; i < n; ++i) {
            d[i] = i;
        }
        for (int i = n - 2; i >= 0; --i) {
            if (s[i] <= s[d[i + 1]]) {
                d[i] = d[i + 1];
            }
        }
        for (int i = 0; i < n; ++i) {
            int j = d[i];
            if (s[i] < s[j]) {
                char t = s[i];
                s[i] = s[j];
                s[j] = t;
                break;
            }
        }
        return Integer.parseInt(String.valueOf(s));
    }
}