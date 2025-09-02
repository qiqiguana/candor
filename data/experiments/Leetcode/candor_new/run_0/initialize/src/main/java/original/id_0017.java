package original;

import java.util.ArrayList;
import java.util.List;
class Solution0017 {
    public List<String> letterCombinations(String digits) {
  /**
  Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order. A mapping of digits to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters. &nbsp; Example 1: Input: digits = &quot;23&quot; Output: [&quot;ad&quot;,&quot;ae&quot;,&quot;af&quot;,&quot;bd&quot;,&quot;be&quot;,&quot;bf&quot;,&quot;cd&quot;,&quot;ce&quot;,&quot;cf&quot;] Example 2: Input: digits = &quot;&quot; Output: [] Example 3: Input: digits = &quot;2&quot; Output: [&quot;a&quot;,&quot;b&quot;,&quot;c&quot;] &nbsp; Constraints: 0 &lt;= digits.length &lt;= 4 digits[i] is a digit in the range [&#39;2&#39;, &#39;9&#39;].
  */
        List<String> ans = new ArrayList<>();
        if (digits.length() == 0) {
            return ans;
        }
        ans.add("");
        String[] d = new String[] {"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        for (char i : digits.toCharArray()) {
            String s = d[i - '2'];
            List<String> t = new ArrayList<>();
            for (String a : ans) {
                for (String b : s.split("")) {
                    t.add(a + b);
                }
            }
            ans = t;
        }
        return ans;
    }
}