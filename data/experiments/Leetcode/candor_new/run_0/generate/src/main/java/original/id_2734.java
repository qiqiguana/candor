package original;

class Solution2734 {
    public String smallestString(String s) {
  /**
  Given a string s consisting of lowercase English letters. Perform the following operation: Select any non-empty substring then replace every letter of the substring with the preceding letter of the English alphabet. For example, &#39;b&#39; is converted to &#39;a&#39;, and &#39;a&#39; is converted to &#39;z&#39;. Return the lexicographically smallest string after performing the operation. &nbsp; Example 1: Input: s = &quot;cbabc&quot; Output: &quot;baabc&quot; Explanation: Perform the operation on the substring starting at index 0, and ending at index 1 inclusive. Example 2: Input: s = &quot;aa&quot; Output: &quot;az&quot; Explanation: Perform the operation on the last letter. Example 3: Input: s = &quot;acbbc&quot; Output: &quot;abaab&quot; Explanation: Perform the operation on the substring starting at index 1, and ending at index 4 inclusive. Example 4: Input: s = &quot;leetcode&quot; Output: &quot;kddsbncd&quot; Explanation: Perform the operation on the entire string. &nbsp; Constraints: 1 &lt;= s.length &lt;= 3 * 105 s consists of lowercase English letters
  */
        int n = s.length();
        int i = 0;
        while (i < n && s.charAt(i) == 'a') {
            ++i;
        }
        if (i == n) {
            return s.substring(0, n - 1) + "z";
        }
        int j = i;
        char[] cs = s.toCharArray();
        while (j < n && cs[j] != 'a') {
            cs[j] = (char) (cs[j] - 1);
            ++j;
        }
        return String.valueOf(cs);
    }
}