package original;

class Solution2272 {
    public int largestVariance(String s) {
  /**
  The variance of a string is defined as the largest difference between the number of occurrences of any 2 characters present in the string. Note the two characters may or may not be the same. Given a string s consisting of lowercase English letters only, return the largest variance possible among all substrings of s. A substring is a contiguous sequence of characters within a string. &nbsp; Example 1: Input: s = &quot;aababbb&quot; Output: 3 Explanation: All possible variances along with their respective substrings are listed below: - Variance 0 for substrings &quot;a&quot;, &quot;aa&quot;, &quot;ab&quot;, &quot;abab&quot;, &quot;aababb&quot;, &quot;ba&quot;, &quot;b&quot;, &quot;bb&quot;, and &quot;bbb&quot;. - Variance 1 for substrings &quot;aab&quot;, &quot;aba&quot;, &quot;abb&quot;, &quot;aabab&quot;, &quot;ababb&quot;, &quot;aababbb&quot;, and &quot;bab&quot;. - Variance 2 for substrings &quot;aaba&quot;, &quot;ababbb&quot;, &quot;abbb&quot;, and &quot;babb&quot;. - Variance 3 for substring &quot;babbb&quot;. Since the largest possible variance is 3, we return it. Example 2: Input: s = &quot;abcde&quot; Output: 0 Explanation: No letter occurs more than once in s, so the variance of every substring is 0. &nbsp; Constraints: 1 &lt;= s.length &lt;= 104 s consists of lowercase English letters.
  */
        int n = s.length();
        int ans = 0;
        for (char a = 'a'; a <= 'z'; ++a) {
            for (char b = 'a'; b <= 'z'; ++b) {
                if (a == b) {
                    continue;
                }
                int[] f = new int[] {0, -n};
                for (int i = 0; i < n; ++i) {
                    if (s.charAt(i) == a) {
                        f[0]++;
                        f[1]++;
                    } else if (s.charAt(i) == b) {
                        f[1] = Math.max(f[0] - 1, f[1] - 1);
                        f[0] = 0;
                    }
                    ans = Math.max(ans, f[1]);
                }
            }
        }
        return ans;
    }
}