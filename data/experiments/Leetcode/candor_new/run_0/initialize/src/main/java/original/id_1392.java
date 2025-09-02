package original;

class Solution1392 {
    private long[] p;
    private long[] h;

    public String longestPrefix(String s) {
  /**
  A string is called a happy prefix if is a non-empty prefix which is also a suffix (excluding itself). Given a string s, return the longest happy prefix of s. Return an empty string &quot;&quot; if no such prefix exists. &nbsp; Example 1: Input: s = &quot;level&quot; Output: &quot;l&quot; Explanation: s contains 4 prefix excluding itself (&quot;l&quot;, &quot;le&quot;, &quot;lev&quot;, &quot;leve&quot;), and suffix (&quot;l&quot;, &quot;el&quot;, &quot;vel&quot;, &quot;evel&quot;). The largest prefix which is also suffix is given by &quot;l&quot;. Example 2: Input: s = &quot;ababab&quot; Output: &quot;abab&quot; Explanation: &quot;abab&quot; is the largest prefix which is also suffix. They can overlap in the original string. &nbsp; Constraints: 1 &lt;= s.length &lt;= 105 s contains only lowercase English letters.
  */
        int base = 131;
        int n = s.length();
        p = new long[n + 10];
        h = new long[n + 10];
        p[0] = 1;
        for (int i = 0; i < n; ++i) {
            p[i + 1] = p[i] * base;
            h[i + 1] = h[i] * base + s.charAt(i);
        }
        for (int l = n - 1; l > 0; --l) {
            if (get(1, l) == get(n - l + 1, n)) {
                return s.substring(0, l);
            }
        }
        return "";
    }

    private long get(int l, int r) {
        return h[r] - h[l - 1] * p[r - l + 1];
    }
}