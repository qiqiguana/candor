package original;

class Solution1400 {
    public boolean canConstruct(String s, int k) {
  /**
  Given a string s and an integer k, return true if you can use all the characters in s to construct non-empty k palindrome strings or false otherwise. &nbsp; Example 1: Input: s = &quot;annabelle&quot;, k = 2 Output: true Explanation: You can construct two palindromes using all characters in s. Some possible constructions &quot;anna&quot; + &quot;elble&quot;, &quot;anbna&quot; + &quot;elle&quot;, &quot;anellena&quot; + &quot;b&quot; Example 2: Input: s = &quot;leetcode&quot;, k = 3 Output: false Explanation: It is impossible to construct 3 palindromes using all the characters of s. Example 3: Input: s = &quot;true&quot;, k = 4 Output: true Explanation: The only possible solution is to put each character in a separate string. &nbsp; Constraints: 1 &lt;= s.length &lt;= 105 s consists of lowercase English letters. 1 &lt;= k &lt;= 105
  */
        int n = s.length();
        if (n < k) {
            return false;
        }
        int[] cnt = new int[26];
        for (int i = 0; i < n; ++i) {
            ++cnt[s.charAt(i) - 'a'];
        }
        int x = 0;
        for (int v : cnt) {
            x += v & 1;
        }
        return x <= k;
    }
}