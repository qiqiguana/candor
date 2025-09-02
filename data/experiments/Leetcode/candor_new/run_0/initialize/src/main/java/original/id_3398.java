package original;

class Solution3398 {
    private char[] s;
    private int numOps;

    public int minLength(String s, int numOps) {
  /**
  You are given a binary string s of length n and an integer numOps. You are allowed to perform the following operation on s at most numOps times: Select any index i (where 0 &lt;= i &lt; n) and flip s[i]. If s[i] == &#39;1&#39;, change s[i] to &#39;0&#39; and vice versa. You need to minimize the length of the longest substring of s such that all the characters in the substring are identical. Return the minimum length after the operations. &nbsp; Example 1: Input: s = &quot;000001&quot;, numOps = 1 Output: 2 Explanation:&nbsp; By changing s[2] to &#39;1&#39;, s becomes &quot;001001&quot;. The longest substrings with identical characters are s[0..1] and s[3..4]. Example 2: Input: s = &quot;0000&quot;, numOps = 2 Output: 1 Explanation:&nbsp; By changing s[0] and s[2] to &#39;1&#39;, s becomes &quot;1010&quot;. Example 3: Input: s = &quot;0101&quot;, numOps = 0 Output: 1 &nbsp; Constraints: 1 &lt;= n == s.length &lt;= 1000 s consists only of &#39;0&#39; and &#39;1&#39;. 0 &lt;= numOps &lt;= n
  */
        this.numOps = numOps;
        this.s = s.toCharArray();
        int l = 1, r = s.length();
        while (l < r) {
            int mid = (l + r) >> 1;
            if (check(mid)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    private boolean check(int m) {
        int cnt = 0;
        if (m == 1) {
            char[] t = {'0', '1'};
            for (int i = 0; i < s.length; ++i) {
                if (s[i] == t[i & 1]) {
                    ++cnt;
                }
            }
            cnt = Math.min(cnt, s.length - cnt);
        } else {
            int k = 0;
            for (int i = 0; i < s.length; ++i) {
                ++k;
                if (i == s.length - 1 || s[i] != s[i + 1]) {
                    cnt += k / (m + 1);
                    k = 0;
                }
            }
        }
        return cnt <= numOps;
    }
}