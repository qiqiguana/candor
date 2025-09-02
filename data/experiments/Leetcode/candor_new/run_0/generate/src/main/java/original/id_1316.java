package original;

import java.util.HashSet;
import java.util.Set;
class Solution1316 {
    private long[] h;
    private long[] p;

    public int distinctEchoSubstrings(String text) {
  /**
  Return the number of distinct non-empty substrings of text&nbsp;that can be written as the concatenation of some string with itself (i.e. it can be written as a + a&nbsp;where a is some string). &nbsp; Example 1: Input: text = &quot;abcabcabc&quot; Output: 3 Explanation: The 3 substrings are &quot;abcabc&quot;, &quot;bcabca&quot; and &quot;cabcab&quot;. Example 2: Input: text = &quot;leetcodeleetcode&quot; Output: 2 Explanation: The 2 substrings are &quot;ee&quot; and &quot;leetcodeleetcode&quot;. &nbsp; Constraints: 1 &lt;= text.length &lt;= 2000 text&nbsp;has only lowercase English letters.
  */
        int n = text.length();
        int base = 131;
        h = new long[n + 10];
        p = new long[n + 10];
        p[0] = 1;
        for (int i = 0; i < n; ++i) {
            int t = text.charAt(i) - 'a' + 1;
            h[i + 1] = h[i] * base + t;
            p[i + 1] = p[i] * base;
        }
        Set<Long> vis = new HashSet<>();
        for (int i = 0; i < n - 1; ++i) {
            for (int j = i + 1; j < n; j += 2) {
                int k = (i + j) >> 1;
                long a = get(i + 1, k + 1);
                long b = get(k + 2, j + 1);
                if (a == b) {
                    vis.add(a);
                }
            }
        }
        return vis.size();
    }

    private long get(int i, int j) {
        return h[j] - h[i - 1] * p[j - i + 1];
    }
}